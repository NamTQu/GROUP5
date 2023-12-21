package com.example.JWT.repository;

import com.example.JWT.dto.Response.ContractResponse;
import com.example.JWT.model.Entity.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    @Query(value = """
            SELECT Bedsitter.room_code, Contract.effective_from, Contract.time_for_rent, Contract.effective_to, Contract.contract_value, Contract.created_at, Contract.status
            FROM Bedsitter INNER JOIN Contract ON Bedsitter.bedsitter_id = Contract.bedsitter_id
            WHERE Contract.tenant_id = :tenantId
            """, nativeQuery = true)
    Page<ContractResponse> findByTenantId(@Param("tenantId") Integer tenantId, Pageable pageable);


}
