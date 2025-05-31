package com.Boitumelo_perez.TaskManager.service;

import com.Boitumelo_perez.TaskManager.model.Task;
import com.Boitumelo_perez.TaskManager.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
// import java.time.LocalDate;
import java.util.Arrays;
// import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void createTask_SavesToRepository() {
        Task task = new Task("Test", "Desc", Task.TaskStatus.PENDING);
        when(taskRepository.save(task)).thenReturn(task);

        Task saved = taskService.createTask(task);
        assertEquals("Test", saved.getTitle());
        verify(taskRepository).save(task);
    }

    @Test
    void getWeeklyCompletion_CalculatesCorrectly() {
        Task completed = new Task("T1", "D1", Task.TaskStatus.COMPLETED);
        Task pending = new Task("T2", "D2", Task.TaskStatus.PENDING);
        when(taskRepository.findByCreatedAtBetween(any(), any()))
            .thenReturn(Arrays.asList(completed, pending));

        double percentage = taskService.getWeeklyCompletionPercentage();
        assertEquals(50.0, percentage);
    }
}