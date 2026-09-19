package com.marketplace.marketplace.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, UUID> {
    Optional<CartEntity> findByUserId(UUID userId);

    @Query("Select c from CartEntity c JOIN FETCH c.user u where u.email = :userEmail")
    Optional<CartEntity> findByUserEmail(@Param("userEmail") String userEmail);
}
