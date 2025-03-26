package com.hackathon.inditex.dto;

import lombok.Data;

@Data
public class OrderResponseDTO {
    private Long orderId;
    private Long customerId;
    private String size;
    private String assignedLogisticsCenter;
    private CoordinatesDTO coordinates;
    private String status;
    private String message;
}
