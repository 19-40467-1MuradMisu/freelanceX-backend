package com.freelancex.notificationservice.dto.event.contract;

import com.freelancex.notificationservice.enums.ContractStatus;
import java.util.UUID;

public record CreateContractEvent(
        UUID contractId,
        UUID userId,
        ContractStatus status
) {}
