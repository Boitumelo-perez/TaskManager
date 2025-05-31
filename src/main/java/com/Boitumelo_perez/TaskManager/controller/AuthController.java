package com.Boitumelo_perez.TaskManager.controller;

import com.Boitumelo_perez.TaskManager.dto.AuthRequest;
import com.Boitumelo_perez.TaskManager.dto.AuthResponse;
import com.Boitumelo_perez.TaskManager.model.User;
import com.Boitumelo_perez.TaskManager.security.JwtUtil;
import com.Boitumelo_perez.TaskManager.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public AuthController(AuthenticationManager authManager, 
                        JwtUtil jwtUtil, 
                        UserService userService) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(), 
                request.getPassword()
            )
        );
        String token = jwtUtil.generateToken(request.getUsername());
        return new AuthResponse(token);
    }

    @PostMapping("/register")
    public User register(@RequestBody AuthRequest request) {
        return userService.registerUser(request);
    }
}