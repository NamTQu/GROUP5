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
    private String room_code;
    private int size;
    private double electric_price;
    private double water_price;
    private double room_price;
    private String description;
}
