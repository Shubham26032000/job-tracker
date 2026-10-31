package com.shubham.job_tracker.security;

import com.shubham.job_tracker.exception.CustomAccessDeniedHandler;
import com.shubham.job_tracker.exception.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    public SecurityConfig(CustomAuthenticationEntryPoint customAuthenticationEntryPoint, CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .cors(Customizer.withDefaults())
//                .csrf(AbstractHttpConfigurer::disable)
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(auth->
                auth
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/api/v1/job/").hasRole("USER")
                        .requestMatchers("/api/v1/user/users").hasRole("ADMIN")
                        .requestMatchers("/api/v1/user",
                                "/api/v1/user/").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception ->
                         exception.authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler))
                .httpBasic(httpBasic -> httpBasic
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                );
        return http.build();
    }

    /***
     * It provides a BCrypt encoder that can hash passwords and verify submitted passwords against stored hashes.
     * (it won't encrypt the password and decrypt during login it just follow above process.
     * @return BCryptPasswordEncoder
     *
     * encode(rawPassword) creates a password hash.
     *
     * matches(rawPassword, storedHash) checks whether a submitted password matches that hash.
     *
     * The bean does not automatically hash passwords or save them to your database. Your application must call it when creating or updating a user's password.
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /***
     * Configuration	Meaning
     * allowedOrigins	Which frontend origins are allowed
     * allowedMethods	Which HTTP methods are allowed
     * allowedHeaders	Which request headers are allowed
     * allowCredentials	Allows credentials such as cookies and HTTP authentication credentials
     * registerCorsConfiguration	Applies this policy to API paths
     * @return
     */

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
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

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);

        return source;
    }

}
