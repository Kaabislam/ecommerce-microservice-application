package com.kaab.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    private final String[] freeResourceUrls = {"/swagger-ui.html","/swagger-ui/**","/v3/api-docs/**",
            "/swagger-resource/**","/api-docs/**","/aggregate/**","/aggregate/inventory-service/v3/api-docs"
    };
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity.authorizeHttpRequests(authorize-> authorize
                        .requestMatchers(freeResourceUrls)
                        .permitAll()
                        .anyRequest()
                .authenticated())
                .oauth2ResourceServer(oauth2->oauth2.jwt(Customizer.withDefaults()))
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri("http://localhost:8181/realms/spring-microservices-security-realm/protocol/openid-connect/certs")
                .build();
    }
}
