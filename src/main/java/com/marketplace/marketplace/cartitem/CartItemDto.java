package com.marketplace.marketplace.cartitem;

import java.math.BigDecimal;
import java.util.UUID;

public class CartItemDto {

    private UUID cartId;
    private UUID itemId;
    private int quantity;
    private String itemName;
    private String itemDescription;
    private BigDecimal price;

    public CartItemDto() {
    }

    public CartItemDto(UUID cartId, UUID itemId, int quantity, String itemName, String itemDescription, BigDecimal price) {
        this.cartId = cartId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.price = price;
    }

    public static CartItemDto fromEntity(CartItemEntity cartItem) {
        if (cartItem == null) {
            return null;
        }

        return new CartItemDto(
                cartItem.getCart() != null ? cartItem.getCart().getId() : null,
                cartItem.getItem() != null ? cartItem.getItem().getId() : null,
                cartItem.getQuantity(),
                cartItem.getItem() != null ? cartItem.getItem().getName() : null,
                cartItem.getItem() != null ? cartItem.getItem().getDescription() : null,
                cartItem.getItem() != null ? cartItem.getItem().getPrice() : null
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

    public BigDecimal getPrice() {
        return price;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getItemName() {
        return itemName;
    }
}
