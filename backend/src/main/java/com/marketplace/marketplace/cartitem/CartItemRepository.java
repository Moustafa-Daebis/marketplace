package com.marketplace.marketplace.cartitem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, CartItemEntity.CartItemId> {
    List<CartItemEntity> findByCart_Id(UUID cartId);
    Optional<CartItemEntity> findByCart_IdAndItem_Id(UUID cartId, UUID itemId);
}
