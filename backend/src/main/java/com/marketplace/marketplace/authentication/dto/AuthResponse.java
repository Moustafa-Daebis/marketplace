package com.marketplace.marketplace.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Returned to the React app for both /login and /register.
 * The frontend should store "token" (e.g. in memory or an httpOnly-cookie
 * equivalent) and send it as: Authorization: Bearer <token>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String token;
    private String tokenType;
}
