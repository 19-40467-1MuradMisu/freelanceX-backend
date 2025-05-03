package com.freelancex.biddingservice.dtos.event.payment;

import com.freelancex.biddingservice.enums.ContractStatus;

import java.util.UUID;

public record CompletePaymentEvent(
        UUID contractId,
        ContractStatus status
) {
}
