package com.marketplace.marketplace.cart;

import java.util.UUID;

public class CartDto {

    private UUID id;
    private UUID userId;

    public CartDto() {
    }

    public CartDto(UUID id, UUID userId) {
        this.id = id;
        this.userId = userId;
    }

    public static CartDto fromEntity(CartEntity cart) {
        return new CartDto(
                cart.getId(),
                cart.getUser() != null ? cart.getUser().getId() : null
        );
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
