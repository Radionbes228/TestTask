package com.radion.taskmanagementsystems.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User {

    @Schema(description = "User ID", example = "123")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(example = "Sokolov")
    @NotBlank(message = "{not.blank.invalid}")
    @Column(nullable = false)
    private String firstName;

    @Schema(example = "Ivan")
    @NotBlank(message = "{not.blank.invalid}")
    @Column(nullable = false)
    private String name;

    @Schema(example = "testemail@gmail.com")
    @NotBlank(message = "{not.blank.invalid}")
    @Email(message = "{email.invalid}")
    @Column(unique = true, nullable = false)
    private String email;

    @Schema(example = "111password111")
    @NotBlank(message = "{not.blank.invalid}")
    @Column(nullable = false)
    @JsonIgnore
    private String password;
}
