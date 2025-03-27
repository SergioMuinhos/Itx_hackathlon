package com.hackathon.inditex.dto;

import lombok.Data;

@Data
public class CenterResponseDTO {
    private Long id;
    private String name;
    private String capacity;
    private String status;
    private Integer maxCapacity;
    private Integer currentLoad;
    private CoordinatesDTO coordinates;

}
