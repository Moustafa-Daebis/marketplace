package com.marketplace.marketplace.authentication.dto;

public record ChangePasswordRequest(
        String currentPassword,
        String newPassword
) {}