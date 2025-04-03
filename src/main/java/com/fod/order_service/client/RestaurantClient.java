package com.fod.order_service.client;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "restaurant-service")
public interface RestaurantClient {

    //example
    //remove this
    @GetMapping("/restaurant/{restaurant-id}/menu")
    @LoadBalanced
    List<String> findAllMenuItemByRestaurantId(@PathVariable("restaurant-id") int restaurantId);
}
