package com.fod.order_service.entity.Enum;

public enum PaymentStatus {
    PENDING,   // Payment is yet to be completed
    PAID,      // Payment is successful
    FAILED,    // Payment attempt failed
    REFUNDED,  // Payment was refunded to the customer
    CANCELED   // Payment was canceled before processing
}
