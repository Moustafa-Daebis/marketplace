package com.marketplace.marketplace.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class OrderDto {

    private UUID id;
    private UUID userId;
    private BigDecimal totalAmount;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public OrderDto() {
    }

    public OrderDto(UUID id, UUID userId, BigDecimal totalAmount, String status,
                    LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static OrderDto fromEntity(OrderEntity order) {
        return new OrderDto(
                order.getId(),
                order.getUser() != null ? order.getUser().getId() : null,
                order.getTotalAmount(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getUpdatedAt()
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

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
