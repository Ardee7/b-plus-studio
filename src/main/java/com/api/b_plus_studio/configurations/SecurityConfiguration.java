package com.api.b_plus_studio.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {


//    private final CustomOAuth2UserService customOAuth2UserService;
//
//    public SecurityConfiguration(CustomOAuth2UserService customOAuth2UserService) {
//        this.customOAuth2UserService = customOAuth2UserService;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        Allow all endpoints

        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Allow all requests without authentication
                )
                .csrf(csrf -> csrf.disable()) // Disable CSRF if needed
                .oauth2Login(oauth2 -> oauth2.disable()) // Disable OAuth2 login
                .formLogin(form -> form.disable()) // Disable form-based login
                .httpBasic(httpBasic -> httpBasic.disable()); // Disable basic authentication

//        Keep Security But Allow Specific Endpoints
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/", "/public/**", "/api/health").permitAll() // Allow specific endpoints
//                        .anyRequest().authenticated() // Require login for others
//                )
//                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}