package com.example.JWT.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BedsitterResponse {
    private Long id;
    private String roomCode;
    private int size;
    private double electricityPrice;
    private double waterPrice;
    private double roomPrice;
    private String description;
}
