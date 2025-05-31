package com.Boitumelo_perez.TaskManager.controller;

import com.Boitumelo_perez.TaskManager.dto.AuthRequest;
import com.Boitumelo_perez.TaskManager.model.User;
import com.Boitumelo_perez.TaskManager.security.JwtUtil;
import com.Boitumelo_perez.TaskManager.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@Import(AuthControllerTest.SecurityTestConfig.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    void login_ValidCredentials_ReturnsToken() throws Exception {
        // Arrange
        String username = "testuser";
        String password = "testpass";
        String token = "fake.jwt.token";

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);

        Mockito.when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password))).thenReturn(authentication);

        Mockito.when(jwtUtil.generateToken(username)).thenReturn(token);

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

        Mockito.when(userService.registerUser(Mockito.any(AuthRequest.class)))
                .thenReturn(mockUser);

        // Act & Assert
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"newuser\",\"password\":\"pass\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("newuser"));
    }

    @Test
    void login_InvalidCredentials_ReturnsUnauthorized() throws Exception {
        Mockito.when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"wrong\",\"password\":\"wrong\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void register_InvalidRequest_ReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"\",\"password\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @TestConfiguration
    static class SecurityTestConfig {
        // Empty configuration - @MockBean handles all mocks
    }
}