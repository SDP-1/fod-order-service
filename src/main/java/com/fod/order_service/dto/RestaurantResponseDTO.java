package com.fod.order_service.dto;

import com.fod.order_service.entity.Enum.CuisineType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantResponseDTO {
    private String id;
    private String name;
    private AddressDTO address;
    private CuisineType cuisineType;
    private String phoneNumber;
    private String email;
    private Double averageRating;
    private Integer reviewCount;
    private Integer estimatedDeliveryTime;
    private Double deliveryFee;
    private List<String> operatingHours;
    private List<String> imageUrls;
    private Boolean isPromoted;
    private List<String> dietaryPreferences;
    private Date createdAt;
    private Date updatedAt;
    private boolean isActive;
}
