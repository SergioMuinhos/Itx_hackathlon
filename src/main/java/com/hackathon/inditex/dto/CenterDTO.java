package com.hackathon.inditex.dto;

import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.processing.Pattern;

@Data
public class CenterDTO {
    @NotNull
    private String name;
    private String capacity;
    private String status;
    private Integer maxCapacity;
    private Integer currentLoad;
    private CoordinatesDTO coordinates;
}