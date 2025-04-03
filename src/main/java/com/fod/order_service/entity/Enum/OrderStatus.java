package com.fod.order_service.entity.Enum;

public enum OrderStatus {
    PENDING,          // Order placed but not confirmed by the restaurant
    CONFIRMED,        // Order accepted by the restaurant
    PREPARING,        // Restaurant is preparing the food
    READY_FOR_PICKUP, // Food is ready for the delivery rider
    OUT_FOR_DELIVERY, // Rider has picked up the order
    DELIVERED,        // Order successfully delivered to the customer
    CANCELED          // Order was canceled (by user, restaurant, or due to payment failure)
}

