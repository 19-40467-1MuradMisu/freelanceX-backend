package com.freelancex.biddingservice.services.interfaces;

import com.freelancex.biddingservice.dtos.api.contract.*;
import com.freelancex.biddingservice.dtos.event.contract.UpdateContractEvent;
import com.freelancex.biddingservice.exceptions.ApiException;

import java.util.UUID;

public interface ContractService {
    GetContractsResponse getContractsByUserId(UUID userId);

    GetContractResponse getContractByUserId(UUID contractId, UUID userId);

    GetContractsResponse getContractsByClientId(UUID clientId);

    GetContractResponse getContractByClientId(UUID contractId, UUID clientId) throws ApiException;

    CreateContractResponse createContract(CreateContractRequest request) throws ApiException;

    UpdateContractResponse updateContractTerms(UUID contractId, UUID clientId,
                                               UpdateContractRequest request) throws ApiException;

    void updateContractStatus(UpdateContractEvent event);
}
