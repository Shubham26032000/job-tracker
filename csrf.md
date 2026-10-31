## CSRF (Cross-Site Request Forgery)

### 1. What is CSRF?

CSRF is an attack in which a malicious website tricks an authenticated user's browser into sending an unwanted request to another website.

**Example:**
1. User logs into `bank.com`.
2. Browser stores the authentication session cookie.
3. User visits a malicious website.
4. Malicious website triggers a request to `bank.com`.
5. Browser may automatically attach the stored session cookie.
6. Bank may process the request because the user appears authenticated.

### 2. Why does CSRF happen?

Browsers automatically attach certain credentials, especially session cookies, to matching requests.

The server cannot automatically distinguish between a legitimate user action and a forged request.

**Important:** Authentication verifies the user's identity; it does not prove that the user intentionally initiated a request.

### 3. How does CSRF protection work?

Spring Security uses a CSRF token to protect against forged requests.

Flow:
1. Server generates a CSRF token.
2. Client obtains the token.
3. Client sends the token with state-changing requests.
4. Spring Security validates the token.
5. Valid token → Request proceeds.
6. Missing or invalid token → HTTP 403 Forbidden.

### 4. HTTP Methods and CSRF

| HTTP Method | CSRF token required by default? |
|---|---|
| GET | No |
| HEAD | No |
| OPTIONS | No |
| TRACE | No |
| POST | Yes |
| PUT | Yes |
| PATCH | Yes |
| DELETE | Yes |

### 5. CSRF Configuration in Spring Security

**Disable CSRF:**

```java
.csrf(AbstractHttpConfigurer::disable)
```

**Enable CSRF:**

```java
.csrf(Customizer.withDefaults())
```

Spring Security enables CSRF protection by default.

### 6. CSRF and Authentication Mechanisms

| Authentication mechanism | CSRF consideration |
|---|---|
| Session-based authentication | CSRF protection generally required |
| HTTP Basic Authentication | Can be vulnerable because browsers may automatically send cached credentials |
| JWT manually sent in Authorization header | Generally not vulnerable to traditional CSRF |
| JWT stored in cookies | CSRF protection generally required |

**Important:** Stateless authentication does not automatically mean CSRF protection is unnecessary. How credentials are stored and transmitted matters.

### 7. Practical Implementation in Job Tracker

We enabled CSRF and tested it using Insomnia.

| Scenario | Result |
|---|---|
| CSRF disabled + POST | Success |
| CSRF enabled + POST without token | 403 Forbidden |
| CSRF enabled + GET | 200 OK |
| CSRF enabled + POST with valid token | Success |

**Retrieve CSRF token:**

```java
@GetMapping("/csrf-token")
public CsrfToken getCsrfToken(CsrfToken csrfToken) {
    return csrfToken;
}
```

Import:

```java
import org.springframework.security.web.csrf.CsrfToken;
```

The response contains:
- `headerName`
- `token`
- `parameterName`

**Send token in Insomnia:**

```http
X-CSRF-TOKEN: <received-token>
```

Keep Basic Authentication enabled while sending the request.

Note: The `/csrf-token` endpoint was created for learning/testing. Production applications need an appropriate CSRF token storage and delivery strategy.

### 8. CORS vs CSRF

| CORS | CSRF |
|---|---|
| Controls cross-origin browser access to responses | Protects against forged authenticated requests |
| Uses origin and HTTP header permissions | Uses CSRF token validation |
| Browser-enforced mechanism | Server-side request protection |
| Does not authenticate users | Does not authenticate users |

### Interview Questions

**Q1. Why does Spring Security enable CSRF by default?**

To protect applications that use automatically attached credentials, such as session cookies.

**Q2. Why can HTTP Basic Authentication be vulnerable to CSRF?**

Browsers may automatically attach cached Basic Authentication credentials to requests.

**Q3. Why does GET generally not require a CSRF token?**

GET is expected to be a safe, read-only HTTP method and should not modify application state.

**Q4. Is disabling CSRF safe for every REST API?**

No. It depends on the authentication mechanism and how credentials are stored and transmitted.