package com.marketplace.marketplace.orderitem;

import com.marketplace.marketplace.order.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, UUID> {
    @Query("UPDATE OrderItemEntity oi SET oi.status = :status WHERE oi.id = :orderItemId")
    int updateUserStatus(@Param("status") String status, @Param("orderItemId") UUID userId);

    @Query("""
    SELECT oi
    FROM OrderItemEntity oi
    JOIN oi.item i
    WHERE i.seller.id = :sellerId
      AND oi.id = :orderItemId
""")
    Optional<OrderItemEntity> findBySellerIdAndOrderItemId(
            @Param("sellerId") UUID sellerId,
            @Param("orderItemId") UUID orderItemId
    );

    @Query("""
    SELECT oi
    FROM OrderItemEntity oi
    JOIN oi.item i
    WHERE i.seller.id = :sellerId
""")
    Optional<OrderItemEntity> findOrdersItemsBySellerId(
            @Param("sellerId") UUID sellerId
    );
}
