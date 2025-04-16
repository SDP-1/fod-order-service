package com.fod.order_service.dto;

import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemResponseDTO {
    private MenuItemResponseDTO menuItem;
    private Integer quantity;
    private List<String> customizations;
    private Double totalPrice;
    private Double totalDiscount;
    private Double netTotalPrice;
}