package com.fod.order_service.controller;

import com.fod.order_service.entity.Enum.OrderStatus;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fod.order_service.dto.OrderRequestDTO;
import com.fod.order_service.dto.OrderResponseDTO;
import com.fod.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderRequestDTO requestDTO) {
        OrderResponseDTO responseDTO = orderService.createOrder(requestDTO);
         return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable String id) {
        Optional<OrderResponseDTO> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/admin")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        List<OrderResponseDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByUserId(@PathVariable String userId) {
        List<OrderResponseDTO> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByRestaurantId(@PathVariable String restaurantId) {
        List<OrderResponseDTO> orders = orderService.getOrdersByRestaurantId(restaurantId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/status/{orderStatus}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByStatus(@PathVariable String orderStatus) {
        List<OrderResponseDTO> orders = orderService.getOrdersByStatus(orderStatus);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/user/{userId}/status/{orderStatus}")
    public ResponseEntity<List<OrderResponseDTO>> findByOrderStatusAndUserId(@PathVariable String orderStatus ,@PathVariable String userId) {
        List<OrderResponseDTO> orders = orderService.findByOrderStatusAndUserId(orderStatus , userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/restaurant/{restaurantId}/status/{orderStatus}")
    public ResponseEntity<List<OrderResponseDTO>> findByOrderStatusAndRestaurantId(@PathVariable String orderStatus ,@PathVariable String restaurantId) {
        List<OrderResponseDTO> orders = orderService.findByOrderStatusAndRestaurantId(orderStatus , restaurantId);
        return ResponseEntity.ok(orders);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> updateOrder(@PathVariable String id, @Valid @RequestBody OrderRequestDTO requestDTO) {
        OrderResponseDTO updatedOrder = orderService.updateOrder(id, requestDTO);
        if (updatedOrder != null) {
            return ResponseEntity.ok(updatedOrder);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/user/{userId}/latest")
    public ResponseEntity<OrderResponseDTO> getLatestOrderByUserId(@PathVariable String userId) {
        Optional<OrderResponseDTO> latestOrder = orderService.getLatestOrderByUserId(userId);
        return latestOrder.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{orderId}/status/{newStatus}")
    public void updateOrderStatus(
            @PathVariable String orderId,
            @PathVariable  OrderStatus newStatus
            ) {
       orderService.updateOrderStatusById(orderId,newStatus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
