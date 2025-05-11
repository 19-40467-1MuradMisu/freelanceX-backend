package com.freelancex.biddingservice.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.freelancex.biddingservice.dtos.api.contract.CreateContractRequest;
import com.freelancex.biddingservice.dtos.api.contract.UpdateContractRequest;
import com.freelancex.biddingservice.models.Contract;
import com.freelancex.biddingservice.services.interfaces.ContractService;
import com.freelancex.biddingservice.views.Views;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/contract")
public class ContractController {
    private final ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping("/freelancer/{freelancerId}")
    @JsonView(Views.FreelancerContractView.class)
    public ResponseEntity<List<Contract>> getContractsByFreelancerId(
            @PathVariable UUID freelancerId) {
        List<Contract> contracts = this.contractService.getContractsByFreelancerId(freelancerId);
        return ResponseEntity.ok(contracts);
    }

    @GetMapping("/client/{clientId}")
    @JsonView(Views.ClientContractView.class)
    public ResponseEntity<List<Contract>> getContractsByClientId(
            @PathVariable("clientId") UUID clientId) {
        List<Contract> contracts = this.contractService.getContractsByClientId(clientId);
        return ResponseEntity.ok(contracts);
    }

    @GetMapping("{id}/client/{clientId}")
    @JsonView(Views.ClientContractView.class)
    public ResponseEntity<Contract> getContractByClientId(@PathVariable UUID id,
                                                                       @PathVariable UUID clientId) {
        Contract contract = this.contractService.getContractByClientId(id, clientId);
        return ResponseEntity.ok(contract);
    }

    @PostMapping("/client")
    public ResponseEntity<Void> createContract(
            @RequestBody @Valid CreateContractRequest request) {
        this.contractService.createContract(request);
        return ResponseEntity.ok(null);
    }

    @PatchMapping("/{id}/client/{clientId}")
    public ResponseEntity<Void> updateContract(@PathVariable UUID id,
                                                      @PathVariable UUID clientId,
                                                      @RequestBody @Valid UpdateContractRequest request) {
        this.contractService.updateContractTerms(id, clientId, request);
        return ResponseEntity.ok(null);
    }
}
