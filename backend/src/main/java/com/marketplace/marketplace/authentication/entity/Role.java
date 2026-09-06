package com.marketplace.marketplace.authentication.entity;

/**
 * Stored in the "role" VARCHAR(50) column via @Enumerated(EnumType.STRING).
 * Add more roles here as needed (e.g. MANAGER, SUPPORT...).
 */
public enum Role {
    USER,
    ADMIN
}
