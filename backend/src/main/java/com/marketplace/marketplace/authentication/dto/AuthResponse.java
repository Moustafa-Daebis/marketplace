package com.marketplace.marketplace.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Response returned after /login and /register. For server-side (form) login the server maintains an HTTP session; no JWT token is issued.
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
}
