package com.Boitumelo_perez.TaskManager.service;

import com.Boitumelo_perez.TaskManager.dto.AuthRequest;
import com.Boitumelo_perez.TaskManager.model.User;
import com.Boitumelo_perez.TaskManager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, 
                     PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(AuthRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepo.save(user);
    }
}