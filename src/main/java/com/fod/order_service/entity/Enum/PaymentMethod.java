package com.fod.order_service.entity.Enum;

public enum PaymentMethod {
    CARD,    // Payment via credit or debit card
    CASH,    // Cash on delivery
    UPI,     // Unified Payments Interface (e.g., Google Pay, PhonePe, etc.)
    WALLET,  // E-wallet payment (e.g., Paytm, Razorpay, etc.)
    NET_BANKING, // Online banking payment
    GIFT_CARD // Payment through gift cards or vouchers
}
