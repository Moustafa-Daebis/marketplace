package com.marketplace.marketplace.cartitem;

import java.util.UUID;

public class CartItemDto {

    private UUID cartId;
    private UUID itemId;
    private int quantity;

    public CartItemDto() {
    }

    public CartItemDto(UUID cartId, UUID itemId, int quantity) {
        this.cartId = cartId;
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public static CartItemDto fromEntity(CartItemEntity cartItem) {
        if (cartItem == null) {
            return null;
        }

        return new CartItemDto(
                cartItem.getCart() != null ? cartItem.getCart().getId() : null,
                cartItem.getItem() != null ? cartItem.getItem().getId() : null,
                cartItem.getQuantity()
        );
    }

    public UUID getCartId() {
        return cartId;
    }

    public void setCartId(UUID cartId) {
        this.cartId = cartId;
    }

    public UUID getItemId() {
        return itemId;
    }

    public void setItemId(UUID itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
