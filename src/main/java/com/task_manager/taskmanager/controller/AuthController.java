package com.task_manager.taskmanager.controller;

import com.task_manager.taskmanager.dto.JwtResponse;
import com.task_manager.taskmanager.dto.LoginRequest;
import com.task_manager.taskmanager.dto.SignupRequest;
import com.task_manager.taskmanager.entity.User;
import com.task_manager.taskmanager.repository.UserRepository;
import com.task_manager.taskmanager.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository; // NEW: Inject to get user role

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody SignupRequest request) {
        authService.signup(request);
        return ResponseEntity.ok(Map.of("message", "User Registered Successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        String token = authService.login(request);

        // Find the user to get their role
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        // Return both token and role
        return ResponseEntity.ok(new JwtResponse(token, user.getRole().name()));
    }
}