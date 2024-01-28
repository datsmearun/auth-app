package com.arun.restaurant.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        http.csrf(t -> t.disable());
        http.authorizeHttpRequests(authorize -> {
            authorize
                    .requestMatchers(HttpMethod.GET, "/restaurant/public/list").permitAll()
                    .requestMatchers(HttpMethod.GET, "/restaurant/public/menu/*").permitAll()
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                    .anyRequest().authenticated();
        });
        http.oauth2ResourceServer(t-> {

            t.jwt(Customizer.withDefaults()); //-> This is with default config for the converter (for JWT auth within resource server)
//            t.opaqueToken(Customizer.withDefaults()); -> This is same as above with (Opaque Token Auth)
//            t.jwt(configurer -> configurer.jwtAuthenticationConverter(jwtAuthConverter)); // -> This is with custom JWTAuth converter to convert the KC roles to Spring boot's format oauth roles

        });
        http.sessionManagement(
                t -> t.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        return http.build();
    }

    //This bean is to override the 'ROLE_' from Security Expression
    @Bean
    public DefaultMethodSecurityExpressionHandler msecurity() {
        DefaultMethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler =
                new DefaultMethodSecurityExpressionHandler();
        defaultMethodSecurityExpressionHandler.setDefaultRolePrefix("");
        return defaultMethodSecurityExpressionHandler;
    }

    //Configuring the roles as default config for the JwtAuthConfig
    //Now we do not need the custom JwtAuthenticationConverter
    @Bean
    public JwtAuthenticationConverter con() {
        JwtAuthenticationConverter c =new JwtAuthenticationConverter();
        JwtGrantedAuthoritiesConverter cv = new JwtGrantedAuthoritiesConverter();
        cv.setAuthorityPrefix(""); // Default "SCOPE_"
        cv.setAuthoritiesClaimName("roles"); // Default "scope" or "scp"
        c.setJwtGrantedAuthoritiesConverter(cv);
        return c;
    }

}
