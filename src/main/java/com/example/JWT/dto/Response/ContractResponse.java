package com.example.JWT.dto.Response;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;

//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
public interface ContractResponse {
//    private String room_code;
//    private Date effective_from;
//    private int time_for_fent;
//    private Date effective_to;
//    private BigDecimal contract_value;
//    private Date created_at;
//    private String status;

    String getroom_code();
    Date geteffective_from();
    Integer gettime_for_rent();
    Date geteffective_to();
    BigDecimal getcontract_value();
    Date getcreated_at();
    String getstatus();
}
