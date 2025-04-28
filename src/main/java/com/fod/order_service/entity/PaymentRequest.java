package com.fod.order_service.entity;

import lombok.Data;

@Data
public class PaymentRequest {
    private String userId;
    private String orderId;
    private String restaurantId;
    private double amount;
    private String currency;
    private String description;
}

