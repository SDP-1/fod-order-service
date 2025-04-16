package com.fod.order_service.service;

import com.fod.order_service.client.RestaurantClient;
import com.fod.order_service.dto.OrderRequestDTO;
import com.fod.order_service.dto.OrderResponseDTO;
import com.fod.order_service.entity.Order;
import com.fod.order_service.repositoty.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RestaurantClient restaurantClient;

    public OrderResponseDTO createOrder(OrderRequestDTO requestDTO) {
        Order order = modelMapper.map(requestDTO, Order.class);
        order.calculateTotalAmount();
        Order savedOrder = orderRepository.save(order);
        return modelMapper.map(savedOrder, OrderResponseDTO.class);
    }

    public Optional<OrderResponseDTO> getOrderById(String id) {
        return orderRepository.findById(id)
                .map(order -> modelMapper.map(order, OrderResponseDTO.class));
    }

    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(order -> modelMapper.map(order, OrderResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<OrderResponseDTO> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(order -> modelMapper.map(order, OrderResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<OrderResponseDTO> getOrdersByRestaurantId(String restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(order -> modelMapper.map(order, OrderResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<OrderResponseDTO> getOrdersByStatus(String orderStatus) {
        return orderRepository.findByOrderStatus(orderStatus)
                .stream()
                .map(order -> modelMapper.map(order, OrderResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<OrderResponseDTO> findByOrderStatusAndUserId(String orderStatus , String userId) {
        return orderRepository.findByOrderStatusAndUserId(orderStatus , userId)
                .stream()
                .map(order -> modelMapper.map(order, OrderResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<OrderResponseDTO> findByOrderStatusAndRestaurantId(String orderStatus , String restaurantId) {
        return orderRepository.findByOrderStatusAndRestaurantId(orderStatus , restaurantId)
                .stream()
                .map(order -> modelMapper.map(order, OrderResponseDTO.class))
                .collect(Collectors.toList());
    }

    public OrderResponseDTO updateOrder(String id, OrderRequestDTO requestDTO) {
        Optional<Order> existingOrder = orderRepository.findById(id);
        if (existingOrder.isPresent()) {
            Order order = existingOrder.get();
            modelMapper.map(requestDTO, order);
            order.calculateTotalAmount();
            Order updatedOrder = orderRepository.save(order);
            return modelMapper.map(updatedOrder, OrderResponseDTO.class);
        }
        return null;
    }

    public void deleteOrder(String id) {
        orderRepository.deleteById(id);
    }
}
