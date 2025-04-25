package com.fod.order_service.controller;

import com.fod.order_service.dto.CartItemRequestDTO;
import com.fod.order_service.dto.CartResponseDTO;
import com.fod.order_service.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartResponseDTO> addToCart(
            @RequestHeader("userId") String userId,
            @Valid @RequestBody CartItemRequestDTO requestDTO) {
        CartResponseDTO cart = cartService.addToCart(userId, requestDTO);
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartResponseDTO> getCart(@PathVariable String userId) {
        CartResponseDTO cart = cartService.getCart(userId);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(@RequestHeader("userId") String userId) {
        cartService.clearCart(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/item/{menuItemId}")
    public ResponseEntity<CartResponseDTO> removeItem(
            @RequestHeader("userId") String userId,
            @PathVariable("menuItemId") String menuItemId) {
        CartResponseDTO cart = cartService.removeItemById(userId, menuItemId);
        return ResponseEntity.ok(cart);
    }
}
