package com.fod.order_service.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PaymentRequest {
    @Positive
    private long amount;

    @NotBlank
    private String currency;

    @NotBlank
    private String description;

    @NotBlank
    private String userId;

    private String restaurantId;

    @NotBlank
    private String successUrl;

    @NotBlank
    private String cancelUrl;
}


