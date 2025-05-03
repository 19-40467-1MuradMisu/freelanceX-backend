package com.freelancex.paymentservice.dtos.event.payment;

import com.freelancex.paymentservice.enums.ContractStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CompletePaymentEvent(@NotNull UUID contractId, @NotBlank ContractStatus status) {
}
