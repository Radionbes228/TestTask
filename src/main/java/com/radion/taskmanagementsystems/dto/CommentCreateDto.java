package com.radion.taskmanagementsystems.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentCreateDto {

    @Schema(description = "Comment text", example = "text", maximum = "1000", minimum = "2")
    @NotEmpty(message = "{not.empty.invalid}")
    @Size(message = "{size.invalid}", min = 2, max = 1000)
    private String text;

    @Schema(description = "Comment author id", example = "122")
    @NotNull(message = "{not.null.invalid}")
    private Long authorId;

    @Schema(description = "Comment assignee id", example = "111")
    @NotNull(message = "{not.null.invalid}")
    private Long taskId;
}
