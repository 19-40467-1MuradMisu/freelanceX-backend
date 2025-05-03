package com.freelancex.paymentservice.dtos.event.contract;

import com.freelancex.paymentservice.enums.ContractStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record CreateContractEvent(
        @NotNull UUID contractId, @NotNull @Positive Double amount, @NotBlank ContractStatus status) {
}
