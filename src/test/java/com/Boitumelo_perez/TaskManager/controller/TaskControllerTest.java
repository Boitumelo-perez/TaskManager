package com.Boitumelo_perez.TaskManager.controller;

import com.Boitumelo_perez.TaskManager.model.Task;
import com.Boitumelo_perez.TaskManager.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
@Import(TaskControllerTest.MockConfig.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @TestConfiguration
    static class MockConfig {
        @Bean
        public TaskService taskService() {
            return MockitoAnnotations.openMocks(new Object()) != null
                ? org.mockito.Mockito.mock(TaskService.class)
                : null;
        }
    }

    @Test
    @WithMockUser
    void getAllTasks_ReturnsTasks() throws Exception {
        Task task = new Task();
        task.setTitle("Test Task");

        when(taskService.getAllTasks()).thenReturn(Collections.singletonList(task));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Task"));
    }
}
