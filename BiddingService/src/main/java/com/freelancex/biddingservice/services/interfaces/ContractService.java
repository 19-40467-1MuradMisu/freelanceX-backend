package com.freelancex.biddingservice.services.interfaces;

import com.freelancex.biddingservice.dtos.api.contract.CreateContractRequest;
import com.freelancex.biddingservice.dtos.api.contract.UpdateContractRequest;
import com.freelancex.biddingservice.dtos.event.payment.CompletePaymentEvent;
import com.freelancex.biddingservice.exceptions.ApiException;
import com.freelancex.biddingservice.models.Contract;

import java.util.List;
import java.util.UUID;

public interface ContractService {
    List<Contract> getContractsByFreelancerId(UUID userId);

    List<Contract> getContractsByClientId(UUID clientId);

    Contract getContractByClientId(UUID contractId, UUID clientId) throws ApiException;

    Contract getContractByFreelancerId(UUID contractId, UUID freelancerId) throws ApiException;

    void createContract(CreateContractRequest request) throws ApiException;

    void updateContractTerms(UUID contractId, UUID clientId,
                                               UpdateContractRequest request) throws ApiException;

    void updateContractStatus(CompletePaymentEvent event);
}
