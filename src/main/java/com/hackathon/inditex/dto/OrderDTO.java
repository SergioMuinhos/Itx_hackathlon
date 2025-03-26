package com.hackathon.inditex.dto;

import lombok.Data;

@Data
public class OrderDTO {
    private Long customerId;
    private String size;
    private CoordinatesDTO coordinates;
}
