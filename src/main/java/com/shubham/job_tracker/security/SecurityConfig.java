package com.shubham.job_tracker.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->
                auth
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/api/v1/job/").hasRole("USER")
//                        .requestMatchers("/api/v1/user/users").hasRole("ADMIN")
//                        .requestMatchers("/api/v1/user/users").hasAuthority("ADMIN")
                        .requestMatchers("/api/v1/user",
                                "/api/v1/user/").permitAll()
                        .anyRequest().authenticated()
                )
        .httpBasic(Customizer.withDefaults());

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

}
