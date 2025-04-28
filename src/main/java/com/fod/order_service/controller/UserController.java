package com.fod.order_service.controller;

import com.fod.order_service.dto.UpdateUserDetailsRequestDTO;
import com.fod.order_service.dto.UserDetailsResponseDTO;
import com.fod.order_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-details")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}") // Use /{userId} for variable path
    public ResponseEntity<UserDetailsResponseDTO> getUserDetailsById(@PathVariable("userId") String userId){
        UserDetailsResponseDTO userDetails = userService.getUserDetailsById(userId); // Call service method to fetch user details
        if (userDetails != null) {
            return ResponseEntity.ok(userDetails); // Return 200 OK with user details
        } else {
            return ResponseEntity.notFound().build(); // Return 404 Not Found if user does not exist
        }
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserDetailsResponseDTO> updateUserDetails(
            @PathVariable String userId,
            @RequestBody UpdateUserDetailsRequestDTO request) {
        UserDetailsResponseDTO updatedUser = userService.updateUserDetails(userId, request);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
