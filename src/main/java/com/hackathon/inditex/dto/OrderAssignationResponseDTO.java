package com.hackathon.inditex.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderAssignationResponseDTO {
    private List<ProcessedOrderDTO> processedOrders;
}
