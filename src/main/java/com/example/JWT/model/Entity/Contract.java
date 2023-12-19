package com.example.JWT.model.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "Contract")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    private Long contractId;

    @ManyToOne
    @JoinColumn(name = "tenant_id", referencedColumnName = "id")
    private User tenant;

    @ManyToOne
    @JoinColumn(name = "bedsitter_id", referencedColumnName = "bedsitter_id")
    private Bedsitter bedsitter;

    @Column(name = "effective_from")
    @Temporal(TemporalType.DATE)
    private Date effectiveFrom;

    @Column(name = "time_for_rent", nullable = false)
    private int timeForRent;

    @Column(name = "effective_to")
    @Temporal(TemporalType.DATE)
    private Date effectiveTo;

    @Column(name = "contract_value", nullable = false)
    private BigDecimal contractValue;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

}
