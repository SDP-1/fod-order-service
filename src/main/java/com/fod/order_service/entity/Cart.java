package com.fod.order_service.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "carts")
public class Cart extends GenaralModel {
    @Id
    private String id;
    private String userId; // Links to user
    private String restaurantId; // Links to restaurant
    private List<CartItem> items = new ArrayList<>();
}