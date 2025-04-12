package com.fod.order_service.dto;

import com.fod.order_service.entity.Enum.OrderStatus;
import com.fod.order_service.entity.Enum.PaymentMethod;
import com.fod.order_service.entity.Enum.PaymentStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestDTO {
    @NotBlank
    private String userId;

    @NotBlank
    private String restaurantId;

    @NotEmpty
    private List<OrderItemDTO> items;

    @Positive
    private Double deliveryFee;

    @Positive
    private Double taxAmount;

    @NotNull
    private AddressDTO deliveryAddress;

    @NotNull
    private OrderStatus orderStatus;

    @NotNull
    private PaymentMethod paymentMethod;

    @NotNull
    private PaymentStatus paymentStatus;

    private String transactionId;

    private String deliveryPersonId;

    private String estimatedDeliveryTime;

    private String orderNotes;

    private String cancellationReason;
}
