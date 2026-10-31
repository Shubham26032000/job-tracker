So far, you've used Basic Auth with the username user and a generated password. But after Spring Security validates those credentials, where does it keep the authenticated user's identity?
The answer is the SecurityContext, accessed through SecurityContextHolder.
1. Understand the relationship
   HTTP Basic credentials
   Username and password arrive with the request

Authentication process
Credentials are validated

SecurityContextHolder
Provides access to the current SecurityContext

SecurityContext → Authentication4

- Contains the authenticated identity and granted authorities
  -
Know these three terms:
- SecurityContextHolder: the access point for the current security context.
- SecurityContext: holds the current Authentication.
- Authentication: represents the authentication information, including the principal and granted authorities. Before authentication, it can also represent an authentication request containing credentials.

####PasswordEncoder 
-  .encode(YOUR_ACTUAL_PASSWORD)  
   this method encode your passowrd basically it gives the hash code not actual encode.
-  .matches(YOUR_ACTUAL_PASSWORD, encodedPassword)  
   this method will match your actual password with encoded hash if password match it will give true else false.

Component	Responsibility
- UserDetailsService	
    Loads a user's details using their username
- PasswordEncoder 
    Verifies the submitted password against the stored hash
- SecurityFilterChain	
    Defines which requests need authentication and how authentication is handled

- Authorities and Roles in userDetails 
  -
and their respective things in filter chain
 - Final Note
   - when you save userRole as "ROLE_USER" with prefix "ROLE_" then use authorities
   - when normal "USER" then use roles()
   - in filters hasRole always use "USER" without prefix irrespective of how you store in DB and that will not work as expected when you store authorities("USER").
   - hasAuthorities alway match how to added in authorities("USER"). if "USER" then hasAuthorities("USER") will work. hasAuthorities("ROLE_USER") will break. also in this case hasRole("USER") not work .
   - when authorities("ROLE_USER") then hasRole("USER") will work and hasAuthority("ROLE_USER") will work but hasAuthorities("USER") will break.
   - When DB stores ROLE_USER, use .authorities(user.getRole()).
- Summary : 
  - When DB stores USER, use .roles(user.getRole()).
  - hasRole("USER") checks for ROLE_USER, regardless of how that authority was created.
  - hasAuthority("USER") checks exactly USER.
  - hasAuthority("ROLE_USER") checks exactly ROLE_USER.
  - .roles() automatically adds ROLE_; .authorities() does not.