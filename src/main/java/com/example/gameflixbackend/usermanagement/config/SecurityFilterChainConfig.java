package com.example.gameflixbackend.usermanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

public class SecurityFilterChainConfig {

    /**
     * Configures certain endpoints to be publicly accessible, i.e. they do not need verification.
     * //todo: this exposes all endpoints for testing purposes, a validation header may be a more secure approach
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() //for postman
                        .requestMatchers(HttpMethod.GET, "/users/*").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/login/validate").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/users/delete").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
