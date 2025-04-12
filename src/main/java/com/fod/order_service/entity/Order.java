package com.fod.order_service.entity;


import com.fod.order_service.entity.Enum.OrderStatus;
import com.fod.order_service.entity.Enum.PaymentMethod;
import com.fod.order_service.entity.Enum.PaymentStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "orders")
public class Order extends GenaralModel{

    @Id
    private String id; // Unique order ID

    private String userId; // Reference to customer (from user_service)

    private String restaurantId; // Reference to restaurant (from restaurant_service)

    private List<OrderItem> items; // List of food items in the order

    private Double totalAmount; // Total order amount (calculated from items + taxes + fees)

    private Double deliveryFee; // Delivery fee for the order

    private Double taxAmount; // Tax amount for the order

    private Address deliveryAddress; // Delivery address for the order

    private OrderStatus orderStatus; // Current status of the order (e.g., PLACED, DELIVERED)

    private PaymentMethod paymentMethod; // Payment method (e.g., CARD, CASH)

    private PaymentStatus paymentStatus; // Payment status (e.g., PENDING, COMPLETED)

    private String transactionId; // Reference to payment transaction (from payment_service)

    private String deliveryPersonId; // Reference to delivery person (from delivery_service)

    private String estimatedDeliveryTime; // Estimated delivery time (e.g., "30-40 mins")

    private String orderNotes; // Optional notes from customer (e.g., "No onions")

    private String cancellationReason; // Reason if order is cancelled

    public void calculateTotalAmount() {
        double itemsTotal = items.stream()
                .mapToDouble(OrderItem::getTotalPrice)
                .sum();
        this.totalAmount = itemsTotal + (taxAmount != null ? taxAmount : 0) + (deliveryFee != null ? deliveryFee : 0);
    }
}
