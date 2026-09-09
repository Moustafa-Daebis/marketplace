package com.marketplace.marketplace.authentication.service;

import com.marketplace.marketplace.authentication.dto.AuthResponse;
import com.marketplace.marketplace.authentication.dto.LoginRequest;
import com.marketplace.marketplace.authentication.dto.RegisterRequest;
import com.marketplace.marketplace.authentication.entity.Role;
import com.marketplace.marketplace.authentication.entity.User;
import com.marketplace.marketplace.authentication.exception.EmailAlreadyExistsException;
import com.marketplace.marketplace.authentication.repository.UserRepositoryAuthorization;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepositoryAuthorization userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        String normalizedEmail = request.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new EmailAlreadyExistsException("An account with this email already exists");
        }

        User user = User.builder()
                .firstName(request.getFirstName().trim())
                .lastName(request.getLastName().trim())
                .email(normalizedEmail)
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .role(request.getRole() != null ? request.getRole() : Role.USER)
                .isActive(true)
                .build();

        User savedUser = userRepository.save(user);

        return buildAuthResponse(savedUser);
    }

    public AuthResponse login(LoginRequest request) {
        String normalizedEmail = request.getEmail().trim().toLowerCase();

        // Delegates to CustomUserDetailsService + the configured PasswordEncoder.
        // Throws BadCredentialsException / DisabledException on failure, both
        // handled centrally by GlobalExceptionHandler.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(normalizedEmail, request.getPassword())
        );

        User user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        return buildAuthResponse(user);
    }

    private AuthResponse buildAuthResponse(User user) {
        return AuthResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
