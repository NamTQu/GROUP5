package com.example.JWT.model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Bedsitter")
public class Bedsitter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bedsitter_id")
    private Long bedsitterId;

    @ManyToOne
    @JoinColumn(name = "tenant_id", referencedColumnName = "id")
    private User tenant;

    @Column(name = "room_code", nullable = false, length = 50)
    private String roomCode;

    @Column(name = "size", nullable = false)
    private int size;

    @Column(name = "electricity_price", nullable = false)
    private double electricityPrice;

    @Column(name = "water_price", nullable = false)
    private double waterPrice;

    @Column(name = "room_price", nullable = false)
    private double roomPrice;

    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

}
