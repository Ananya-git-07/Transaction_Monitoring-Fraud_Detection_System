package com.example.transaction.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
                .name("bearerAuth") // The name used in our Controllers (@SecurityRequirement)
                .type(SecurityScheme.Type.HTTP) // HTTP authentication
                .scheme("bearer") // Bearer token format
                .bearerFormat("JWT"); // Specify that it's a JSON Web Token

        Components components = new Components().addSecuritySchemes("bearerAuth", securityScheme);
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                .info(new Info()
                        .title("Enterprise AML Fraud Detection API")
                        .version("1.0")
                        .description("A complete financial transaction and rule-based fraud detection system.")
                        .contact(new Contact().name("Your Name").email("your.email@example.com")) // Add your details!
                )
                .components(components)
                .addSecurityItem(securityRequirement);
    }
}
