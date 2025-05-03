package com.freelancex.paymentservice.services.interfaces;

import com.freelancex.paymentservice.dtos.event.contract.CreateContractEvent;

public interface ContractService {
    void CreateContract(CreateContractEvent event);
}
