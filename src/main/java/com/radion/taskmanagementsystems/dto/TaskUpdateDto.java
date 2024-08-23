package com.radion.taskmanagementsystems.dto;


import com.radion.taskmanagementsystems.entity.enums.Priority;
import com.radion.taskmanagementsystems.entity.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskUpdateDto {


    @Schema(description = "Task id", example = "22")
    @NotNull(message = "{not.null.invalid}")
    private Long id;

    @Schema(example = "title")
    @NotBlank(message = "{not.blank.invalid}")
    @Size(message = "{size.invalid}",min = 5, max = 100)
    private String title;

    private String description;

    @Schema(example = "PENDING")
    @NotNull(message = "{not.null.invalid}")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Schema(example = "HIGH")
    @NotNull(message = "{not.null.invalid}")
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Schema(example = "122")
    @NotNull(message = "{not.null.invalid}")
    private Long authorId;

    @Schema(example = "133")
    private Long assigneeId;
}
