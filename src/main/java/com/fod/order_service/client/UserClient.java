package com.fod.order_service.client;

import com.fod.order_service.dto.UpdateUserDetailsRequestDTO;
import com.fod.order_service.dto.UserDetailsResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-management-service")
public interface UserClient {

    @GetMapping("/api/user-details/{userId}")
    UserDetailsResponseDTO getUserDetailsById(@PathVariable("userId") String userId);

    @PatchMapping("/api/user-details/{userId}")
    UserDetailsResponseDTO updateUserDetails(
            @PathVariable("userId") String userId,
            @RequestBody UpdateUserDetailsRequestDTO request);
}
