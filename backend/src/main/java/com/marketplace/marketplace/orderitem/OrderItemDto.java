package com.marketplace.marketplace.orderitem;

import com.marketplace.marketplace.cartitem.CartItemDto;
import com.marketplace.marketplace.cartitem.CartItemEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
public class OrderItemDto {

    private UUID id;
    private UUID orderId;
    private UUID itemId;
    private int quantity;
    private String itemName;
    private String itemDescription;
    private BigDecimal price;
    private String status;

    public OrderItemDto() {
    }

    public OrderItemDto(UUID id, UUID orderId, UUID itemId, int quantity, String itemName, String itemDescription, BigDecimal price, String status) {
        this.id = id;
        this.orderId = orderId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.price = price;
        this.status = status;
    }

    public static OrderItemDto fromEntity(OrderItemEntity orderItem) {
        if (orderItem == null) {
            return null;
        }

        return new OrderItemDto(
                orderItem.getId(),
                orderItem.getOrder().getId(),
                orderItem.getItem().getId(),
                orderItem.getQuantity(),
                orderItem.getItem().getName(),
                orderItem.getItem().getDescription(),
                orderItem.getItem().getPrice(),
                orderItem.getStatus()
        );
    }

}
