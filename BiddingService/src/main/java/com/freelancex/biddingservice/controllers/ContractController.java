package com.freelancex.biddingservice.controllers;

import com.freelancex.biddingservice.dtos.api.contract.CreateContractRequest;
import com.freelancex.biddingservice.dtos.api.contract.UpdateContractRequest;
import com.freelancex.biddingservice.dtos.common.ApiResponse;
import com.freelancex.biddingservice.models.Contract;
import com.freelancex.biddingservice.services.interfaces.ContractService;
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

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Contract>>> getContractsByUserId(
            @PathVariable("userId") UUID userId) {
        List<Contract> contracts = this.contractService.getContractsByUserId(userId);

        ApiResponse<List<Contract>> response = new ApiResponse<>("success", 200, contracts);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/user/{userId}")
    public ResponseEntity<ApiResponse<Contract>> getContractByUserId(@PathVariable("id") UUID id,
                                                                   @PathVariable("userId") UUID userId) {
        Contract contract = this.contractService.getContractByUserId(id, userId);

        ApiResponse<Contract> response = new ApiResponse<>("success", 200, contract);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<ApiResponse<List<Contract>>> getContractsByClientId(
            @PathVariable("clientId") UUID clientId) {
        List<Contract> contracts = this.contractService.getContractsByClientId(clientId);

        ApiResponse<List<Contract>> response = new ApiResponse<>("success", 200, contracts);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}/client/{clientId}")
    public ResponseEntity<ApiResponse<Contract>> getContractByClientId(@PathVariable("id") UUID id,
                                                                     @PathVariable(
                                                                             "clientId") UUID clientId) {
        Contract contract = this.contractService.getContractByClientId(id, clientId);

        ApiResponse<Contract> response = new ApiResponse<>("success", 200, contract);
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> createContract(
            @RequestBody @Valid CreateContractRequest request) {
        this.contractService.createContract(request);

        ApiResponse response = new ApiResponse<>("success", 200, null);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/client/{clientId}")
    public ResponseEntity<ApiResponse> updateContract(@PathVariable("id") UUID id,
                                                                 @PathVariable("clientId") UUID clientId,
                                                                 @RequestBody @Valid UpdateContractRequest request) {
        this.contractService.updateContractTerms(id, clientId, request);

        ApiResponse response = new ApiResponse<>("success", 200, null);
        return ResponseEntity.ok(response);
    }
}
