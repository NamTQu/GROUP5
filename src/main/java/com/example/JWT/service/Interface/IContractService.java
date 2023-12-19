package com.example.JWT.service.Interface;

import com.example.JWT.dto.Request.ContractRequest;

public interface IContractService {
    public void createContract(ContractRequest request);

    public void cancelContract(Long contractId);
}
