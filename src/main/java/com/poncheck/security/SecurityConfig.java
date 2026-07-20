package com.poncheck.security;

import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
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

import java.util.List;

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
                 .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                 .csrf(AbstractHttpConfigurer::disable)
                 .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                 .authorizeHttpRequests(request -> request
                         .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                         .requestMatchers(HttpMethod.POST, "/auth/register").hasAnyRole("OWNER", "ADMIN")
                         .requestMatchers(HttpMethod.PATCH, "/users/{id}/active").hasAnyRole("OWNER", "ADMIN")
                         .requestMatchers(HttpMethod.DELETE, "/users/{id}").hasRole("ADMIN")
                         .requestMatchers(HttpMethod.GET, "/products", "/categories").hasRole("ADMIN")
                         .requestMatchers(HttpMethod.DELETE, "/products/{id}").hasRole("ADMIN")
                         .requestMatchers(HttpMethod.POST,"/products/", "/products/{id}/active", "/products/prices").hasAnyRole("ADMIN", "OWNER")
                         .requestMatchers(HttpMethod.DELETE, "/categories/{id}").hasRole("ADMIN")
                         .requestMatchers(HttpMethod.POST,"/categories/", "/categories/{id}/active").hasAnyRole("ADMIN", "OWNER")
                         .requestMatchers(HttpMethod.PUT, "/business/{id}").hasAnyRole("OWNER", "ADMIN")
                         .requestMatchers("/business/**").hasRole("ADMIN")
                         .requestMatchers("/api-docs/**", "/swagger-ui/**", "/docs").permitAll()
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
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(
                List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        );
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}


