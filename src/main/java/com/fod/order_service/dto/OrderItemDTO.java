package com.fod.order_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDTO {
    @NotBlank
    private String itemId;

    @NotBlank
    private String itemName;

    @Positive
    private Integer quantity;

    @Positive
    private Double unitPrice;

    @Positive
    private Double totalPrice;

    private List<String> customizations;
}
