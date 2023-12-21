package com.example.JWT.service.Interface;

import com.example.JWT.dto.Request.ContractRequest;
import com.example.JWT.dto.Response.ContractResponse;
import com.example.JWT.model.Entity.Contract;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IContractService {
    public void createContract(ContractRequest request);

    public void cancelContract(Long contractId);
}
