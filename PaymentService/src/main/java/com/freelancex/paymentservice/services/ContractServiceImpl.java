package com.freelancex.paymentservice.services;

import com.freelancex.paymentservice.dtos.event.contract.CreateContractEvent;
import com.freelancex.paymentservice.models.Contract;
import com.freelancex.paymentservice.repositories.ContractRepository;
import com.freelancex.paymentservice.services.interfaces.ContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ContractServiceImpl implements ContractService {
    private final Logger logger = LoggerFactory.getLogger(ContractServiceImpl.class);

    private final ContractRepository contractRepository;

    public ContractServiceImpl(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Override
    public void CreateContract(CreateContractEvent event) {
        Contract contract = new Contract();
        contract.setContractId(event.contractId());
        contract.setStatus(event.status());

        contractRepository.save(contract);
        logger.info("Contract: {} created", contract.getContractId());
    }
}
