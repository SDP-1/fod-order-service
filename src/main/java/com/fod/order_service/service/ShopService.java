package com.fod.order_service.service;

import com.fod.order_service.client.RestaurantClient;
import com.fod.order_service.dto.MenuItemResponseDTO;
import com.fod.order_service.dto.RestaurantResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopService {

    @Autowired
    private RestaurantClient restaurantClient;

    public List<RestaurantResponseDTO> getAllShops() {
        return restaurantClient.getAllRestaurants();
    }

    public RestaurantResponseDTO getShopDetails(String shopId) {
        RestaurantResponseDTO shop = restaurantClient.getRestaurantById(shopId);
        if (shop == null) {
            throw new IllegalArgumentException("Shop not found: " + shopId);
        }
        return shop;
    }

    public List<MenuItemResponseDTO> getShopItems(String shopId) {
        List<MenuItemResponseDTO> items = restaurantClient.getMenuItemsByRestaurant(shopId);
        if (items.isEmpty()) {
            throw new IllegalArgumentException("No items found for shop: " + shopId);
        }
        return items;
    }

    public List<MenuItemResponseDTO> getShopItemsByCategory(String shopId, String category) {
        List<MenuItemResponseDTO> items = restaurantClient.getMenuItemsByRestaurantAndCategory(shopId, category);
        if (items.isEmpty()) {
            throw new IllegalArgumentException("No items found for shop: " + shopId + " in category: " + category);
        }
        return items;
    }
}
