package com.example.apigateway.config;

//import com.example.apigateway.handler.KeycloakLogoutHandler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;


@EnableWebFluxSecurity
@Configuration
public class ApiGatewayConfig {

    @Value("${spring.security.oauth2.client.provider.api-gateway.issuer-uri}")
    private String issuerUrl;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(authorize -> authorize
                        .pathMatchers("/login**", "/error**").permitAll()
                        // .pathMatchers("/tes").hasAnyAuthority("ROLE_default-roles-salon")
                        .anyExchange().authenticated()
                )
                .oauth2Login(Customizer.withDefaults())
                .oauth2Client(Customizer.withDefaults())
        //  .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
        // .logout(logout -> logout.logoutHandler(keycloakLogoutHandler))
        ;
        return http.build();
    }
}