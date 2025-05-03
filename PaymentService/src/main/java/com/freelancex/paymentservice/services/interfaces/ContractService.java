package com.freelancex.paymentservice.services.interfaces;

import com.freelancex.paymentservice.dtos.event.contract.CreateContractEvent;

import java.util.UUID;

public interface ContractService {
    void createContract(CreateContractEvent event);

    void completeContract(UUID contractId);
}
