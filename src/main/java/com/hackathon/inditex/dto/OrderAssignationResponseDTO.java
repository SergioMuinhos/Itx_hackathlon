package com.hackathon.inditex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrderAssignationResponseDTO {
    @JsonProperty("processed-orders")
    private List<ProcessedOrderDTO> processedOrders;
}
