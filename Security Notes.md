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

Contains the authenticated identity and granted authorities
Know these three terms:
- SecurityContextHolder: the access point for the current security context.
- SecurityContext: holds the current Authentication.
- Authentication: represents the authentication information, including the principal and granted authorities. Before authentication, it can also represent an authentication request containing credentials.