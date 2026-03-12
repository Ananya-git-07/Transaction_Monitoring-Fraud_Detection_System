package com.example.transaction.controller;


import com.example.transaction.dto.AuthRequest;
import com.example.transaction.dto.RegisterRequest;
import com.example.transaction.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "1. Authentication", description = "Endpoints for registering and logging in users")
public class AuthController {

    private final AuthService authService;
    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user and returns a JWT token")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        String token = authService.register(request);
        return ResponseEntity.ok(token);
    }
    @PostMapping("/login")
    @Operation(summary = "Log in a user", description = "Authenticates credentials and returns a JWT token")
    public ResponseEntity<String> login(@Valid @RequestBody AuthRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(token);
    }
}
