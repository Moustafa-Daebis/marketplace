package com.marketplace.marketplace.authentication.service;

import com.marketplace.marketplace.authentication.dto.AuthResponse;
import com.marketplace.marketplace.authentication.dto.LoginRequest;
import com.marketplace.marketplace.authentication.dto.RegisterRequest;
import com.marketplace.marketplace.authentication.entity.Role;
import com.marketplace.marketplace.authentication.entity.User;
import com.marketplace.marketplace.authentication.exception.EmailAlreadyExistsException;
import com.marketplace.marketplace.authentication.repository.UserRepositoryAuthorization;
import com.marketplace.marketplace.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepositoryAuthorization userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
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
        String token = jwtService.generateToken(savedUser);

        return buildAuthResponse(savedUser, token);
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

        String token = jwtService.generateToken(user);

        return buildAuthResponse(user, token);
    }

    public void changePassword(String email,String currentPassword,String newPassword){


        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Invalid User"));

        if(!passwordEncoder.matches(currentPassword,user.getPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Incorrect password");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    private AuthResponse buildAuthResponse(User user, String token) {
        return AuthResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .token(token)
                .tokenType("Bearer")
                .build();
    }
}
