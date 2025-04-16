package com.fod.order_service.dto;

import com.fod.order_service.entity.Enum.ItemCategory;
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
public class MenuItemResponseDTO {
    private String id;
    private String restaurantId;
    private String name;
    private String description;
    private Double price;
    private ItemCategory category;
    private boolean isAvailable;
    private List<String> imageUrls;
    private List<String> dietaryTags;
    private Integer calories;
    private List<String> customizationOptions;
    private Double discountPrice;
    private Date createdAt;
    private Date updatedAt;
    private boolean isActive;
}