package com.fod.order_service.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemRequestDTO {
    @NotBlank(message = "Menu item ID is required")
    private String menuItemId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    private List<String> customizations;
}
