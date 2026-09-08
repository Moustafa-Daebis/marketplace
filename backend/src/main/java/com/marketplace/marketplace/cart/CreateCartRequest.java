package com.marketplace.marketplace.cart;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class CreateCartRequest {

    @NotNull(message = "User id is required")
    private UUID userId;

    public CreateCartRequest() {
    }

    public CreateCartRequest(UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
