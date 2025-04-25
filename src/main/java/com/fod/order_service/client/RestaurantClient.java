package com.fod.order_service.client;

import com.fod.order_service.dto.MenuItemResponseDTO;
import com.fod.order_service.dto.RestaurantResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "restaurant-service")
public interface RestaurantClient {

    @GetMapping("/api/restaurants/{id}")
    RestaurantResponseDTO getRestaurantById(@PathVariable("id") String id);

    @GetMapping("/api/restaurants")
    List<RestaurantResponseDTO> getAllRestaurants();

    @GetMapping("/api/menu-items/restaurant/{restaurantId}")
    List<MenuItemResponseDTO> getMenuItemsByRestaurant(@PathVariable("restaurantId") String restaurantId);

    @GetMapping("/api/menu-items/{id}")
    MenuItemResponseDTO getMenuItemById(@PathVariable("id") String id);

    @GetMapping("/api/menu-items/restaurant/{restaurantId}/category/{category}")
    List<MenuItemResponseDTO> getMenuItemsByRestaurantAndCategory(
            @PathVariable("restaurantId") String restaurantId,
            @PathVariable("category") String category);

    @GetMapping("/api/menu-items/restaurant/{restaurantId}/categories")
    List<String> getMenuItemsCategoriesByRestaurantId(
            @PathVariable("restaurantId") String restaurantId);
}
