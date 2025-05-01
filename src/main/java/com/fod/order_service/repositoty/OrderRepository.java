package com.fod.order_service.repositoty;

import com.fod.order_service.entity.Enum.OrderStatus;
import com.fod.order_service.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByUserId(String userId);
    List<Order> findByRestaurantId(String restaurantId);
    List<Order> findByOrderStatus(String orderStatus);
    List<Order> findByOrderStatusAndRestaurantId(String orderStatus,String restaurantId);
    List<Order> findByOrderStatusAndUserId(String orderStatus,String userId);
    Order getOrderById(String orderId);

    Optional<Order> findTopByUserIdAndOrderStatusInOrderByCreatedAtDesc(String userId, List<OrderStatus> orderStatuses);
}
