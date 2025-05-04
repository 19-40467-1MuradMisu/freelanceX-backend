package com.freelancex.notificationservice.dtos.contract;

import com.freelancex.notificationservice.enums.ContractStatus;

import java.util.UUID;

public record CreateContractEvent(
        UUID userId,
        UUID contractId,
        Double amount,
        ContractStatus status
) {
}
