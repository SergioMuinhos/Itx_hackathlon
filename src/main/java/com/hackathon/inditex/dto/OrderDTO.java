package com.hackathon.inditex.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long customerId;
    private String size;
    private CoordinatesDTO coordinates;
}
