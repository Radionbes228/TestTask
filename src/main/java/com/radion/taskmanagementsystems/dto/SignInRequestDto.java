package com.radion.taskmanagementsystems.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Authentication Request")
public class SignInRequestDto {

    @Schema(example = "jondoe@gmail.com")
    @NotBlank(message = "{not.blank.invalid}")
    @Email(message = "{This field is not email}")
    private String email;

    @Schema(example = "my_1secret1_password")
    @NotBlank(message = "{not.blank.invalid}")
    @Size(min = 15,max = 50, message = "{size.invalid}")
    private String password;
}
