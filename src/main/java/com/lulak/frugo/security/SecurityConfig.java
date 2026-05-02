package com.lulak.frugo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .csrf(csrf -> csrf.disable())

                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();

                    config.setAllowedOrigins(
                            List.of("http://localhost:5173", "http://localhost:5174")
                    );

                    config.setAllowedMethods(
                            List.of("GET","POST","PUT","DELETE","OPTIONS")
                    );

                    config.setAllowedHeaders(
                            List.of("*")
                    );

                    config.setExposedHeaders(
                            List.of("Content-Disposition")
                    );

                    return config;
                }))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/shop/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers("/api/admin/**").hasAnyRole("USER","ADMIN")
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
