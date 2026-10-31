## CORS (Cross-Origin Resource Sharing)

### 1. What is CORS?

CORS is a browser-enforced security mechanism that controls whether a frontend running on one origin can access resources from a backend running on another origin.

**Origin consists of:**
- Protocol (HTTP/HTTPS)
- Host (localhost, example.com)
- Port (8080, 5173)

Example:
- React frontend: `http://localhost:5173`
- Spring Boot backend: `http://localhost:8080`

These are different origins because their ports differ.

### 2. Why do we need CORS?

Browsers follow the Same-Origin Policy. By default, JavaScript running on one origin cannot freely access responses from another origin.

CORS allows the backend to explicitly specify which origins are permitted.

### 3. Preflight Request (OPTIONS)

For certain cross-origin requests, the browser first sends an HTTP `OPTIONS` request to check whether the actual request is allowed.

It checks:
- Allowed origin
- Allowed HTTP methods
- Allowed request headers

If permitted, the browser sends the actual request. Otherwise, it blocks the actual request.

### 4. Important CORS Headers

| Header | Purpose |
|---|---|
| `Access-Control-Allow-Origin` | Specifies permitted frontend origin |
| `Access-Control-Allow-Methods` | Specifies permitted HTTP methods |
| `Access-Control-Allow-Headers` | Specifies permitted request headers |
| `Access-Control-Allow-Credentials` | Allows credentialed cross-origin requests |
| `Access-Control-Request-Method` | Method the browser intends to use |
| `Access-Control-Request-Headers` | Headers the browser intends to send |

### 5. CORS Configuration in Spring Security

**Enable CORS in SecurityFilterChain:**

```java
http.cors(Customizer.withDefaults());
```

**Configure CORS policy:**

```java
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();

    configuration.setAllowedOrigins(
        List.of("http://localhost:5173")
    );

    configuration.setAllowedMethods(
        List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
    );

    configuration.setAllowedHeaders(
        List.of("Authorization", "Content-Type")
    );

    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source =
        new UrlBasedCorsConfigurationSource();

    source.registerCorsConfiguration("/**", configuration);

    return source;
}
```

### 6. Important Points

- CORS is enforced by browsers, not by curl.
- CORS is not authentication or authorization.
- CORS does not make protected APIs public.
- Spring Security must handle CORS before authentication so preflight requests are not rejected.
- `allowCredentials(true)` requires explicit allowed origins; wildcard `*` cannot be used for credentialed CORS responses.

### 7. Practical Testing

**Allowed origin:**

`http://localhost:5173` → HTTP 200 with appropriate CORS headers.

**Disallowed origin:**

`http://localhost:3000` → HTTP 403 without an `Access-Control-Allow-Origin` header.

### Interview Question

**Q: What is the difference between CORS and CSRF?**

CORS controls which origins can access cross-origin responses through browsers. CSRF protects authenticated users against unwanted requests initiated by malicious websites.

They solve different security problems.