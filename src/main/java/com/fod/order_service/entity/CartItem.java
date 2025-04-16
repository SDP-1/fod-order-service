package com.fod.order_service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItem {
    private String menuItemId; // Links to MenuItem
    private Integer quantity;
    private List<String> customizations; // E.g., ["Mild"]
}
