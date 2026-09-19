package com.marketplace.marketplace.cartitem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, UUID> {
    List<CartItemEntity> findByCart_Id(UUID cartId);
    Optional<CartItemEntity> findByCart_IdAndItem_Id(UUID cartId, UUID itemId);
    @Query("Select ci from CartItemEntity ci JOIN FETCH ci.item where ci.cart.user.id = :userId")
    List<CartItemEntity> findByUserId(UUID userId);
}
