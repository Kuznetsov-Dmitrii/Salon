package com.example.apigateway.config;

//import com.example.apigateway.handler.KeycloakLogoutHandler;
//import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@Configuration
public class ResourceServerConfig {

    @Value("${spring.security.oauth2.client.provider.api-gateway.issuer-uri}")
    private String issuerUrl;

//    private final KeycloakLogoutHandler keycloakLogoutHandler;
//
//    @Autowired
//    ResourceServerConfig(KeycloakLogoutHandler keycloakLogoutHandler) {
//        this.keycloakLogoutHandler = keycloakLogoutHandler;
//    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
        http
                .authorizeExchange(authorize -> authorize
                        .pathMatchers("oauth2/**","/login**","/error**").permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
                )
                .oauth2Login(Customizer.withDefaults())
                .oauth2Client(Customizer.withDefaults())
                //.logout(logout -> logout.addLogoutHandler(keycloakLogoutHandler).logoutSuccessUrl("/"))
        ;
        return http.build();
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return NimbusReactiveJwtDecoder.withJwkSetUri(this.issuerUrl).build();
    }
}
