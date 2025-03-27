package com.hackathon.inditex.dto;

import lombok.Data;

@Data
public class CenterUpdateDTO {
    private String name;
    private String capacity;
    private String status;
    private Integer currentLoad;
    private Integer maxCapacity;
    private CoordinatesDTO coordinates;
}
