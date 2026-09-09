package com.marketplace.marketplace.cartitem;

import com.marketplace.marketplace.cart.CartEntity;
import com.marketplace.marketplace.item.ItemEntity;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "cart_items")
public class CartItemEntity {

    @EmbeddedId
    private CartItemId id;

    @MapsId("cartId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private CartEntity cart;

    @MapsId("itemId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private ItemEntity item;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public CartItemEntity() {
    }

    public CartItemEntity(CartEntity cart, ItemEntity item, int quantity) {
        this.cart = cart;
        this.item = item;
        this.quantity = quantity;
        this.id = new CartItemId(
                cart != null ? cart.getId() : null,
                item != null ? item.getId() : null
        );
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if (this.id == null) {
            this.id = new CartItemId(
                    this.cart != null ? this.cart.getId() : null,
                    this.item != null ? this.item.getId() : null
            );
        }
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public CartItemId getId() {
        return id;
    }

    public void setId(CartItemId id) {
        this.id = id;
    }

    public CartEntity getCart() {
        return cart;
    }

    public void setCart(CartEntity cart) {
        this.cart = cart;
        if (this.id == null) {
            this.id = new CartItemId();
        }
        this.id.setCartId(cart != null ? cart.getId() : null);
    }

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
        if (this.id == null) {
            this.id = new CartItemId();
        }
        this.id.setItemId(item != null ? item.getId() : null);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Embeddable
    public static class CartItemId implements Serializable {

        @Column(name = "cart_id")
        private UUID cartId;

        @Column(name = "item_id")
        private UUID itemId;

        public CartItemId() {
        }

        public CartItemId(UUID cartId, UUID itemId) {
            this.cartId = cartId;
            this.itemId = itemId;
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CartItemId that = (CartItemId) o;
            return Objects.equals(cartId, that.cartId) && Objects.equals(itemId, that.itemId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(cartId, itemId);
        }
    }
}
