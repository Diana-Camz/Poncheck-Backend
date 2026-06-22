package com.poncheck.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI ponchesOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("PonCheck POS API")
                        .version("v1.0.0")
                        .description("""
                                Multi-tenant Point of Sale API for Ponches businesses.
                                
                                Features:
                                - JWT Authentication
                                - Role-based access control (ADMIN, OWNER, SELLER)
                                - Business management
                                - Users management
                                - Products and categories
                                - Inventory movements
                                - Sales and cancellations
                                - Cash register and cash movements
                                """)
                        .contact(new Contact()
                                .name("Diana Campos")
                                .url("https://github.com/Diana-Camz"))
                )
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        SECURITY_SCHEME_NAME,
                                        new SecurityScheme()
                                                .name(SECURITY_SCHEME_NAME)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList(SECURITY_SCHEME_NAME)
                );
    }
}
