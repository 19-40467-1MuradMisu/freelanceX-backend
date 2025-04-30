package com.freelancex.biddingservice.services;

import com.freelancex.biddingservice.dtos.api.contract.*;
import com.freelancex.biddingservice.dtos.event.contract.CreateContractEvent;
import com.freelancex.biddingservice.dtos.event.contract.UpdateContractEvent;
import com.freelancex.biddingservice.exceptions.ApiException;
import com.freelancex.biddingservice.kafka.interfaces.KafkaProducerService;
import com.freelancex.biddingservice.models.Contract;
import com.freelancex.biddingservice.repositories.ContractRepository;
import com.freelancex.biddingservice.services.interfaces.ContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContractServiceImpl implements ContractService {
    private final static Logger logger = LoggerFactory.getLogger(ContractServiceImpl.class);
    private final ContractRepository contractRepository;
    private final KafkaProducerService kafkaProducerService;

    @Autowired
    public ContractServiceImpl(ContractRepository contractRepository,
                               KafkaProducerService kafkaProducerService) {
        this.contractRepository = contractRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    public GetContractsResponse getContractsByUserId(UUID userId) {
        List<Contract> contracts = contractRepository.findByBidUserId(userId);

        GetContractsResponse response = new GetContractsResponse(contracts);
        response.setMessage("success");
        response.setStatusCode(HttpStatus.OK.value());
        return response;
    }

    @Override
    public GetContractResponse getContractByUserId(UUID contractId, UUID userId) {
        Optional<Contract> contract = contractRepository.findByContractIdAndBidUserId(contractId, userId);

        if (contract.isEmpty()) {
            throw new ApiException("Contract not found", HttpStatus.NOT_FOUND);
        }

        GetContractResponse response = new GetContractResponse(contract.get());
        response.setMessage("success");
        response.setStatusCode(HttpStatus.OK.value());
        return response;
    }

    @Override
    public GetContractsResponse getContractsByClientId(UUID clientId) {
        List<Contract> contracts = contractRepository.findContractByJobUserId(clientId);

        GetContractsResponse response = new GetContractsResponse(contracts);
        response.setMessage("success");
        response.setStatusCode(HttpStatus.OK.value());
        return response;
    }

    @Override
    public GetContractResponse getContractByClientId(UUID contractId, UUID clientId) throws ApiException {
        Optional<Contract> contract = contractRepository.findContractByContractIdAndJobUserId(contractId,
                clientId);

        if (contract.isEmpty()) {
            throw new ApiException("Contract does not exists", HttpStatus.NOT_FOUND);
        }

        GetContractResponse response = new GetContractResponse(contract.get());
        response.setMessage("success");
        response.setStatusCode(HttpStatus.OK.value());
        return response;
    }

    @Override
    public CreateContractResponse createContract(CreateContractRequest request) throws ApiException {
        boolean bidExists = contractRepository.existsContractByBidIdOrJobId(request.getBidId(),
                request.getJobId());

        if (bidExists) {
            throw new ApiException("Contract already exists", HttpStatus.CONFLICT);
        }

        Contract contract = new Contract();
        contract.setBidId(request.getBidId());
        contract.setJobId(request.getJobId());
        contract.setTerms(request.getTerms());

        Contract savedContract = contractRepository.save(contract);

        CreateContractEvent event = new CreateContractEvent(savedContract.getContractId(),
                savedContract.getStatus());
        this.kafkaProducerService.sendContractCreatedEvent(event);

        CreateContractResponse response = new CreateContractResponse();
        response.setMessage("success");
        response.setStatusCode(HttpStatus.CREATED.value());
        return response;
    }

    @Override
    public UpdateContractResponse updateContractTerms(UUID contractId, UUID clientId,
                                                      UpdateContractRequest request) throws ApiException {
        Optional<Contract> contract = contractRepository.findContractByContractIdAndJobUserId(contractId,
                clientId);

        if (contract.isEmpty()) {
            throw new ApiException("Contract does not exists", HttpStatus.NOT_FOUND);
        }

        Contract contractToUpdate = contract.get();
        contractToUpdate.setTerms(request.getTerms());
        contractRepository.save(contractToUpdate);

        UpdateContractResponse response = new UpdateContractResponse();
        response.setMessage("success");
        response.setStatusCode(HttpStatus.OK.value());
        return response;
    }

    @Override
    public void updateContractStatus(UpdateContractEvent event) {
        Optional<Contract> contract = contractRepository.findById(event.contractId());

        if (contract.isPresent()) {
            Contract contractToUpdate = contract.get();
            contractToUpdate.setStatus(event.status());
            contractRepository.save(contractToUpdate);

            logger.info("Contract: {} status updated successfully", event.contractId());
        }
    }
}
