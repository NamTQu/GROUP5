package com.example.JWT.dto.Request;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractRequest {
       private Long tenant_id;
       private Long bedsitter_id;
       private Date effectiveFrom;
       private int timeForRent;
       private Date effectiveTo;
       private BigDecimal contractValue;
       private Date createdAt;
       private String status;

}
