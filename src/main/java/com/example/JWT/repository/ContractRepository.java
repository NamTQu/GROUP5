package com.example.JWT.repository;

import com.example.JWT.model.Entity.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    Page<Contract> findByTenantId(Integer tenantId, Pageable pageable);
}
