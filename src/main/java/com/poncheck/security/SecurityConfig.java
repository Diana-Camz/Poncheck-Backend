package com.poncheck.security;

import com.poncheck.entity.User;
import com.poncheck.repository.UserRepository;
import com.poncheck.service.AuthService;
import com.poncheck.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            AuthenticationProvider authenticationProvider
            ) throws Exception{
         http
                 .csrf(AbstractHttpConfigurer::disable)
                 .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                 .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/register").hasAnyRole("OWNER", "ADMIN")
                         .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/users/{id}/active").hasAnyRole("OWNER", "ADMIN")
                         .requestMatchers(HttpMethod.DELETE, "/api/v1/users/{id}").hasRole("ADMIN")
                         .requestMatchers(HttpMethod.DELETE, "/api/v1/products/{id}").hasRole("ADMIN")
                         .requestMatchers(HttpMethod.POST,"/api/v1/products/", "/api/v1/products/{id}/active").hasAnyRole("ADMIN", "OWNER")
                         .requestMatchers(HttpMethod.DELETE, "/api/v1/categories/{id}").hasRole("ADMIN")
                         .requestMatchers(HttpMethod.POST,"/api/v1/categories/", "/api/v1/categories/{id}/active").hasAnyRole("ADMIN", "OWNER")
                        .anyRequest().authenticated()
                )
                 .authenticationProvider(authenticationProvider)
                 .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

         //esta funcionalidad de logout es mas comun cuando se crean sesiones de forma tradicional con spring security,
        //pero con jwt no hay sesion en el servidor, el front guarda el token por lo que es crear un metodo logout en el controlador auth.
        {/*.logout(logout ->
                         logout.logoutUrl("/auth/logout")
                                 .addLogoutHandler((request, response, authentication) -> {
                                     final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
                                     authService.logout(authHeader);
                                 })
                                 .logoutSuccessHandler((request, response, authentication) ->
                                     SecurityContextHolder.clearContext())
                                 );*/}

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }

}
