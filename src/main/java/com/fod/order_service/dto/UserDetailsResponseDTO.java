package com.fod.order_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDetailsResponseDTO {
    private String id;
    private String username;
    private String fullName;
    private AddressDTO address;
    private boolean isVerified;
    private String role;
}
