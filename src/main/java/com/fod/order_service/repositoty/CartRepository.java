package com.fod.order_service.repositoty;

import com.fod.order_service.entity.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {
    Optional<Cart> findByUserIdAndIsActive(String userId, boolean isActive);
    Optional<Cart> findByUserId(String userId);
}
