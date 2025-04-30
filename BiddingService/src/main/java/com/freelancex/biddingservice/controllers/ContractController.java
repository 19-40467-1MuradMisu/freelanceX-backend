package com.freelancex.biddingservice.controllers;

import com.freelancex.biddingservice.dtos.api.contract.*;
import com.freelancex.biddingservice.services.interfaces.ContractService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController()
@RequestMapping("/api/contract")
public class ContractController {
    private final ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<GetContractsResponse> getContractsByUserId(@PathVariable("userId") UUID userId) {
        GetContractsResponse response = this.contractService.getContractsByUserId(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/user/{userId}")
    public ResponseEntity<GetContractResponse> getContractByUserId(@PathVariable("id") UUID id,
                                                                   @PathVariable("userId") UUID userId) {
        GetContractResponse response = this.contractService.getContractByUserId(id, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<GetContractsResponse> getContractsByClientId(
            @PathVariable("clientId") UUID clientId) {
        GetContractsResponse response = this.contractService.getContractsByClientId(clientId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}/client/{clientId}")
    public ResponseEntity<GetContractResponse> getContractByClientId(@PathVariable("id") UUID id,
                                                                     @PathVariable(
                                                                             "clientId") UUID clientId) {
        GetContractResponse response = this.contractService.getContractByClientId(id, clientId);
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<CreateContractResponse> createContract(
            @RequestBody @Valid CreateContractRequest request) {
        CreateContractResponse response = this.contractService.createContract(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/client/{clientId}")
    public ResponseEntity<UpdateContractResponse> updateContract(@PathVariable("id") UUID id,
                                                                 @PathVariable("clientId") UUID clientId,
                                                                 @RequestBody @Valid UpdateContractRequest request) {
        UpdateContractResponse response = this.contractService.updateContractTerms(id, clientId, request);
        return ResponseEntity.ok(response);
    }
}
