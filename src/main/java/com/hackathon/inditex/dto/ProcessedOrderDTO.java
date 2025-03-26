package com.hackathon.inditex.dto;

import lombok.Data;

@Data
public class ProcessedOrderDTO {
    private Double distance;
    private Long orderId;
    private String assignedLogisticsCenter;
    private String message;
    private String status;
}
