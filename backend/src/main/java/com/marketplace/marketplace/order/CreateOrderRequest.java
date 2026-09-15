package com.marketplace.marketplace.order;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class CreateOrderRequest {

    @NotNull(message = "Cart id is required")
    private UUID cartId;

    public CreateOrderRequest() {
    }

    public CreateOrderRequest(UUID cartId) {
        this.cartId = cartId;
    }

    public UUID getCartId() {
        return cartId;
    }

    public void setCartId(UUID cartId) {
        this.cartId = cartId;
    }
}
