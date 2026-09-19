package com.marketplace.marketplace.authentication.controller;

import com.marketplace.marketplace.authentication.dto.AuthResponse;
import com.marketplace.marketplace.authentication.dto.ChangePasswordRequest;
import com.marketplace.marketplace.authentication.dto.LoginRequest;
import com.marketplace.marketplace.authentication.dto.RegisterRequest;
import com.marketplace.marketplace.authentication.service.AuthService;
import jakarta.persistence.Entity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Only two endpoints, as requested, for the React frontend to consume:
 *   POST /api/auth/register
 *   POST /api/auth/login
 *
 * Both are whitelisted in SecurityConfig (permitAll).
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/changePassword")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordRequest request , Authentication authentication){
        authService.changePassword(authentication.getName(),request.currentPassword(),request.newPassword());
        return ResponseEntity.noContent().build();
    }
}
