package com.radion.taskmanagementsystems.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Size;

@Entity
@Table(name = "comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Schema(description = "Comment id", example = "111")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Comment text")
    @NotBlank(message = "{not.blank.invalid}")
    @Size(message = "{size.invalid}", min = 2, max = 1000)
    @Column(nullable = false)
    private String text;

    @Schema(description = "Comment id author")
    @NotNull(message = "{not.null.invalid}")
    @ManyToOne
    @JoinColumn(nullable = false)
    private User author;

    @Schema(description = "The task to which the comment is attached")
    @NotNull(message = "{not.null.invalid}")
    @ManyToOne
    @JsonIgnore
    @JoinColumn(nullable = false)
    private Task task;
}
