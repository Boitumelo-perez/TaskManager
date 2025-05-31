package com.Boitumelo_perez.TaskManager.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Task entity representing a to-do item")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The unique ID of the task", example = "1")
    private Long id;

    @NotBlank(message = "Title is mandatory")
    @Schema(description = "Title of the task", example = "Complete API documentation")
    private String title;

    @Schema(description = "Detailed description of the task", example = "Add Swagger documentation to all endpoints")
    private String description;

    @Builder.Default
    @NotNull
    @Enumerated(EnumType.STRING)
    @Schema(description = "Current status of the task", example = "PENDING")
    private TaskStatus status = TaskStatus.PENDING;

    @Builder.Default
    @Schema(description = "Date when the task was created", example = "2025-06-01")
    private LocalDate createdAt = LocalDate.now();

    public Task(String title, String description, TaskStatus status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public enum TaskStatus {
        @Schema(description = "Task is pending completion")
        PENDING,
        @Schema(description = "Task has been completed")
        COMPLETED
    }
}
