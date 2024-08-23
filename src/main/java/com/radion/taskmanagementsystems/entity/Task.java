package com.radion.taskmanagementsystems.entity;

import com.radion.taskmanagementsystems.entity.enums.Priority;
import com.radion.taskmanagementsystems.entity.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "task")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Task {

    @Schema(description = "Task ID", example = "123")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Task title", example = "Title")
    @NotBlank(message = "{not.blank.invalid}")
    @Size(message = "{size.invalid}",min = 5, max = 100)
    @Column(nullable = false)
    private String title;

    @Schema(description = "Task description", example = "Good description")
    private String description;

    @Schema(description = "Task Status", example = "PENDING")
    @NotNull(message = "{not.null.invalid}")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Schema(description = "Task priority", example = "LOW")
    @NotNull(message = "{not.null.invalid}")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    @Schema(description = "Task author")
    @NotNull(message = "{not.null.invalid}")
    @ManyToOne
    @JoinColumn(nullable = false)
    private User author;

    @Schema(description = "Task assignee")
    @ManyToOne
    private User assignee;

    @Schema(description = "Task comments")
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
}
