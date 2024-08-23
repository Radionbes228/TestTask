package com.radion.taskmanagementsystems.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "Task Management API",
                description = "API for managing tasks", version = "1.0.0",
                contact = @Contact(
                        name = "Radion",
                        email = "############"
                )
        )
)
@Configuration
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
}
