package com.Boitumelo_perez.TaskManager.controller;

import com.Boitumelo_perez.TaskManager.config.TestSecurityConfig;
import com.Boitumelo_perez.TaskManager.dto.AuthRequest;
import com.Boitumelo_perez.TaskManager.model.User;
import com.Boitumelo_perez.TaskManager.security.JwtUtil;
import com.Boitumelo_perez.TaskManager.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    void login_ValidCredentials_ReturnsToken() throws Exception {
        // Arrange
        String username = "testuser";
        String password = "testpass";
        String token = "test.jwt.token";
        
        when(authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        )).thenReturn(new UsernamePasswordAuthenticationToken(username, null));
        
        when(jwtUtil.generateToken(username)).thenReturn(token);

        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testuser\",\"password\":\"testpass\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token));
    }

    @Test
    void register_NewUser_ReturnsUser() throws Exception {
        // Arrange
        User mockUser = new User();
        mockUser.setUsername("newuser");
        
        when(userService.registerUser(any(AuthRequest.class))).thenReturn(mockUser);

        // Act & Assert
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"newuser\",\"password\":\"pass\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("newuser"));
    }
}