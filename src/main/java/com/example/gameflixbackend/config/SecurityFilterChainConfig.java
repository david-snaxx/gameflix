package com.example.gameflixbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityFilterChainConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/users/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/users/delete").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/login/validate").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/register").permitAll()

                        .requestMatchers(HttpMethod.GET, "/games/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/games/igdb/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/games/igdb/accesstoken").permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}