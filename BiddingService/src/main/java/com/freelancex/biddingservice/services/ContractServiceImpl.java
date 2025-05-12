package com.freelancex.biddingservice.services;

import com.freelancex.biddingservice.dtos.api.contract.CreateContractRequest;
import com.freelancex.biddingservice.dtos.api.contract.UpdateContractRequest;
import com.freelancex.biddingservice.dtos.event.contract.CreateContractEvent;
import com.freelancex.biddingservice.dtos.event.payment.CompletePaymentEvent;
import com.freelancex.biddingservice.exceptions.ApiException;
import com.freelancex.biddingservice.kafka.interfaces.KafkaProducerService;
import com.freelancex.biddingservice.models.Bid;
import com.freelancex.biddingservice.models.Contract;
import com.freelancex.biddingservice.repositories.ContractRepository;
import com.freelancex.biddingservice.services.interfaces.BidService;
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
    private final BidService bidService;
    private final KafkaProducerService kafkaProducerService;

    @Autowired
    public ContractServiceImpl(ContractRepository contractRepository, BidService bidService,
                               KafkaProducerService kafkaProducerService) {
        this.contractRepository = contractRepository;
        this.bidService = bidService;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    public List<Contract> getContractsByFreelancerId(UUID freelancerId) {

        return contractRepository.findByBidFreelancerId(freelancerId);
    }

    @Override
    public List<Contract> getContractsByClientId(UUID clientId) {

        return contractRepository.findByJobClientId(clientId);
    }

    @Override
    public Contract getContractByClientId(UUID contractId, UUID clientId) throws ApiException {
        Optional<Contract> contract = contractRepository.findByContractIdAndJobClientId(contractId,
                clientId);

        if (contract.isEmpty()) {
            throw new ApiException("Contract not found", HttpStatus.NOT_FOUND);
        }

        return contract.get();
    }

    @Override
    public Contract getContractByFreelancerId(UUID contractId, UUID freelancerId) throws ApiException {
        Optional<Contract> contract = contractRepository.findByContractIdAndBidFreelancerId(contractId,
                freelancerId);

        if (contract.isEmpty()) {
            throw new ApiException("Contract not found", HttpStatus.NOT_FOUND);
        }

        return contract.get();
    }

    @Override
    public void createContract(CreateContractRequest request) throws ApiException {
        Optional<Contract> existingContract = contractRepository.findByJobIdOrBidId(request.getJobId(),
                request.getBidId());

        if (existingContract.isPresent()) {
            throw new ApiException("Contract already exists", HttpStatus.CONFLICT);
        }

        Contract contract = new Contract();
        contract.setBidId(request.getBidId());
        contract.setJobId(request.getJobId());
        contract.setTerms(request.getTerms());

        Contract savedContract = contractRepository.save(contract);

        Bid bid = bidService.getBidById(request.getBidId());

        CreateContractEvent event = new CreateContractEvent(bid.getFreelancerId(),
                savedContract.getContractId(), bid.getAmount(),
                savedContract.getStatus());
        this.kafkaProducerService.sendContractCreatedEvent(event);
    }

    @Override
    public void updateContractTerms(UUID contractId, UUID clientId,
                                                      UpdateContractRequest request) throws ApiException {
        Optional<Contract> contract = contractRepository.findByContractIdAndJobClientId(contractId,
                clientId);

        if (contract.isEmpty()) {
            throw new ApiException("Contract not found", HttpStatus.NOT_FOUND);
        }

        Contract contractToUpdate = contract.get();
        contractToUpdate.setTerms(request.getTerms());
        contractRepository.save(contractToUpdate);
    }

    @Override
    public void updateContractStatus(CompletePaymentEvent event) {
        Optional<Contract> contract = contractRepository.findById(event.contractId());

        if (contract.isPresent()) {
            Contract contractToUpdate = contract.get();
            contractToUpdate.setStatus(event.status());
            contractRepository.save(contractToUpdate);

            logger.info("Contract: {} status updated successfully", event.contractId());
        }
    }
}
