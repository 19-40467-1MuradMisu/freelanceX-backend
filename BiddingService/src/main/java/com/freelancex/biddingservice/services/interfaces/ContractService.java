package com.freelancex.biddingservice.services.interfaces;


import com.freelancex.biddingservice.models.Contract;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContractService {
    List<Contract> getAllContracts();

    Optional<Contract> getContractById(UUID id);

    Contract saveContract(Contract contract);

    void deleteContract(UUID id);
}
