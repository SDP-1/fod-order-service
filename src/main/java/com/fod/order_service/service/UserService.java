package com.fod.order_service.service;

import com.fod.order_service.client.UserClient;
import com.fod.order_service.dto.UpdateUserDetailsRequestDTO;
import com.fod.order_service.dto.UserDetailsResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserClient userClient;

    public UserDetailsResponseDTO getUserDetailsById(String userId){
        return userClient.getUserDetailsById(userId);
    }

    public UserDetailsResponseDTO updateUserDetails(String userId, UpdateUserDetailsRequestDTO request) {
        return userClient.updateUserDetails(userId, request);
    }}
