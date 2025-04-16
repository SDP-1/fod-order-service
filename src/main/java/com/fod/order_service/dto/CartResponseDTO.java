package com.fod.order_service.dto;

import lombok.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponseDTO {
    private String id;
    private String userId;
    private RestaurantResponseDTO restaurant;
    private List<CartItemResponseDTO> items;
    private Date createdAt;
    private Date updatedAt;
    private boolean isActive;
}
