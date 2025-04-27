package com.freelancex.biddingservice.services;

import com.freelancex.biddingservice.models.Contract;
import com.freelancex.biddingservice.repositories.ContractRepository;
import com.freelancex.biddingservice.services.interfaces.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContractServiceImpl implements ContractService {
    private final ContractRepository contractRepository;

    @Autowired
    public ContractServiceImpl(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Override
    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    @Override
    public Optional<Contract> getContractById(UUID id) {
        return contractRepository.findById(id);
    }

    @Override
    public Contract saveContract(Contract contract) {
        return contractRepository.save(contract);
    }

    @Override
    public void deleteContract(UUID id) {
        contractRepository.deleteById(id);
    }
}
