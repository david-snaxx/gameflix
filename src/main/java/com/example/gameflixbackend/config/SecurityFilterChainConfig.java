package com.example.gameflixbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
                        .requestMatchers(HttpMethod.GET, "/games/igdb/search/name/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/games/igdb/search/id/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/games/add/igdb/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/games/delete/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/games/get/**").permitAll()

                        .requestMatchers(
                                "/",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/error",
                                "/register",
                                "/register-success",
                                "login",
                                "add-game"
                        ).permitAll()

                        .anyRequest().authenticated()
                )
                // configuring the login page
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                // configuring logout behavior
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );
        return http.build();
    }
}