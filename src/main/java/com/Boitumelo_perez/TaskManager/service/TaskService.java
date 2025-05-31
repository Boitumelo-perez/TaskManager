package com.Boitumelo_perez.TaskManager.service;

import com.Boitumelo_perez.TaskManager.model.Task;
import com.Boitumelo_perez.TaskManager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getTasksByStatus(Task.TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task taskDetails) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setStatus(taskDetails.getStatus());
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public double getWeeklyCompletionPercentage() {
        LocalDate now = LocalDate.now();
        LocalDate startOfWeek = now.with(DayOfWeek.MONDAY);
        List<Task> weeklyTasks = taskRepository.findByCreatedAtBetween(startOfWeek, now);
        
        if (weeklyTasks.isEmpty()) {
            return 0.0;
        }
        
        long completedCount = weeklyTasks.stream()
                .filter(task -> task.getStatus() == Task.TaskStatus.COMPLETED)
                .count();
        
        return (double) completedCount / weeklyTasks.size() * 100;
    }
}