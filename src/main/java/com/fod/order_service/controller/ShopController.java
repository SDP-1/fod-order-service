package com.fod.order_service.controller;

import com.fod.order_service.dto.MenuItemResponseDTO;
import com.fod.order_service.dto.RestaurantResponseDTO;
import com.fod.order_service.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/shops")
@RequiredArgsConstructor
public class ShopController {

    @Autowired
    private ShopService shopService;

    @GetMapping
    public ResponseEntity<List<RestaurantResponseDTO>> getAllShops() {
        List<RestaurantResponseDTO> shops = shopService.getAllShops();
        return ResponseEntity.ok(shops);
    }

    @GetMapping("/{shopId}")
    public ResponseEntity<RestaurantResponseDTO> getShopDetails(@PathVariable String shopId) {
        RestaurantResponseDTO shop = shopService.getShopDetails(shopId);
        return ResponseEntity.ok(shop);
    }

    @GetMapping("/{shopId}/items")
    public ResponseEntity<List<MenuItemResponseDTO>> getShopItems(@PathVariable String shopId) {
        List<MenuItemResponseDTO> items = shopService.getShopItems(shopId);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{shopId}/items/category/{category}")
    public ResponseEntity<List<MenuItemResponseDTO>> getShopItemsByCategory(
            @PathVariable String shopId,
            @PathVariable String category) {
        List<MenuItemResponseDTO> items = shopService.getShopItemsByCategory(shopId, category);
        return ResponseEntity.ok(items);
    }
}