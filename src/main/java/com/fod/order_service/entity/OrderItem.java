package com.fod.order_service.entity;



import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Getter
@Setter
public class OrderItem {
    private String itemId; // Reference to menu item (from restaurant_service)
    private String itemName; // Denormalized for convenience
    private Integer quantity; // Quantity ordered
    private Double unitPrice; // Price per unit (fetched from restaurant_service)
    private Double totalPrice; // quantity * unitPrice
    private List<String> customizations; // Customizations (e.g., "Extra cheese")
}