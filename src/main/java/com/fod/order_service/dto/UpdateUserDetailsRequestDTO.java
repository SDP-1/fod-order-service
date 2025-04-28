package com.fod.order_service.dto;

import lombok.Data;

@Data
public class UpdateUserDetailsRequestDTO {
    private String fullName;
    private String username;
    private String password;
    private AddressDTO address;
    private String role;
}
