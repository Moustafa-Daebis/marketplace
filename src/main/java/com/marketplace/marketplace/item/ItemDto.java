package com.marketplace.marketplace.item;

import java.util.UUID;

public class ItemDto {

    private UUID id;
    private String name;
    private String description;
    private UUID sellerId;

    public ItemDto() {
    }

    public ItemDto(UUID id, String name, String description, UUID sellerId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sellerId = sellerId;
    }

    public static ItemDto fromEntity(ItemEntity item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getSeller() != null ? item.getSeller().getId() : null
        );
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getSellerId() {
        return sellerId;
    }

    public void setSellerId(UUID sellerId) {
        this.sellerId = sellerId;
    }
}
