package com.radion.taskmanagementsystems.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "Task Management API",
                description = "API for managing tasks", version = "1.0.1",
                contact = @Contact(
                        name = "Beslaneev Radion",
                        email = "radionbes@gmail.com"
                )
        )
)
@Configuration
@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        name = "BearerAuth")
public class ConfigSwagger {

    @Bean
    public GroupedOpenApi filteredOpenApiComment() {
        return GroupedOpenApi.builder()
                .group("Comment")
                .pathsToMatch("/api/comments/**")
                .addOperationCustomizer((operation, handlerMethod) -> {
                    operation.getResponses().remove("404");
                    operation.getResponses().remove("502");
                    return operation;
                })
                .build();
    }

    @Bean
    public GroupedOpenApi filteredOpenApiTask() {
        return GroupedOpenApi.builder()
                .group("Task")
                .pathsToMatch("/api/tasks/**")
                .build();
    }

    @Bean
    public GroupedOpenApi filteredOpenApiAuth() {
        return GroupedOpenApi.builder()
                .group("Auth")
                .pathsToMatch("/api/auth/**")
                .addOperationCustomizer(((operation, handlerMethod) -> {
                    operation.getResponses().remove("400");
                    operation.getResponses().remove("403");
                    return operation;
                }))
                .build();
    }
}
