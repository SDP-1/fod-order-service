package com.fod.order_service.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ReviewResponseDTO {
    private String id;
    private String userId;
    private String userName;
    private String targetId;
    private String comment;
    private Double rating;
    private Date createdAt;
    private Date updatedAt;
    private boolean isActive;
}
