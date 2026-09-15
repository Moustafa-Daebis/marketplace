package com.marketplace.marketplace.orderitem;

import com.marketplace.marketplace.item.ItemEntity;
import com.marketplace.marketplace.order.OrderEntity;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "order_items")
public class OrderItemEntity {

    @EmbeddedId
    private OrderItemId id;

    @MapsId("orderId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @MapsId("itemId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private ItemEntity item;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public OrderItemEntity() {
    }

    public OrderItemEntity(OrderEntity order, ItemEntity item, int quantity, BigDecimal price) {
        this.order = order;
        this.item = item;
        this.quantity = quantity;
        this.price = price;
        this.id = new OrderItemId(
                order != null ? order.getId() : null,
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
            this.id = new OrderItemId(
                    this.order != null ? this.order.getId() : null,
                    this.item != null ? this.item.getId() : null
            );
        }
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public OrderItemId getId() {
        return id;
    }

    public void setId(OrderItemId id) {
        this.id = id;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
        if (this.id == null) {
            this.id = new OrderItemId();
        }
        this.id.setOrderId(order != null ? order.getId() : null);
    }

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
        if (this.id == null) {
            this.id = new OrderItemId();
        }
        this.id.setItemId(item != null ? item.getId() : null);
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

    public void setPrice(BigDecimal price) {
        this.price = price;
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
    public static class OrderItemId implements Serializable {

        @Column(name = "order_id")
        private UUID orderId;

        @Column(name = "item_id")
        private UUID itemId;

        public OrderItemId() {
        }

        public OrderItemId(UUID orderId, UUID itemId) {
            this.orderId = orderId;
            this.itemId = itemId;
        }

        public UUID getOrderId() {
            return orderId;
        }

        public void setOrderId(UUID orderId) {
            this.orderId = orderId;
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
            OrderItemId that = (OrderItemId) o;
            return Objects.equals(orderId, that.orderId) && Objects.equals(itemId, that.itemId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(orderId, itemId);
        }
    }
}
