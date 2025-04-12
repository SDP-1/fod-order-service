package com.fod.order_service.dto;

import com.fod.order_service.entity.Enum.OrderStatus;
import com.fod.order_service.entity.Enum.PaymentMethod;
import com.fod.order_service.entity.Enum.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDTO {
    private String id;
    private String userId;
    private String restaurantId;
    private List<OrderItemDTO> items;
    private Double totalAmount;
    private Double deliveryFee;
    private Double taxAmount;
    private AddressDTO deliveryAddress;
    private OrderStatus orderStatus;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private String transactionId;
    private String deliveryPersonId;
    private String estimatedDeliveryTime;
    private String orderNotes;
    private String cancellationReason;
    private Date createdAt;
    private Date updatedAt;
    private boolean isActive;
}

