package com.Boitumelo_perez.TaskManager.controller;

import com.Boitumelo_perez.TaskManager.model.Task;
import com.Boitumelo_perez.TaskManager.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Task API", description = "Operations related to task management")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Get all tasks", description = "Retrieve all tasks with optional status filter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved tasks"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<Task>> getAllTasks(
            @Parameter(description = "Status to filter tasks (PENDING or COMPLETED)")
            @RequestParam(required = false) Task.TaskStatus status) {
        if (status != null) {
            return ResponseEntity.ok(taskService.getTasksByStatus(status));
        }
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @Operation(summary = "Create a new task", description = "Create a new task with title, description and status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Task> createTask(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Task object to be created")
            @RequestBody Task task) {
        return ResponseEntity.ok(taskService.createTask(task));
    }

    @Operation(summary = "Update a task", description = "Update an existing task by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Task> updateTask(
            @Parameter(description = "ID of the task to be updated")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated task object")
            @RequestBody Task task) {
        return ResponseEntity.ok(taskService.updateTask(id, task));
    }

    @Operation(summary = "Delete a task", description = "Delete a task by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Void> deleteTask(
            @Parameter(description = "ID of the task to be deleted")
            @PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get completion stats", description = "Get percentage of completed tasks for current week")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved stats"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/stats/completion")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Double> getWeeklyCompletionPercentage() {
        return ResponseEntity.ok(taskService.getWeeklyCompletionPercentage());
    }
}