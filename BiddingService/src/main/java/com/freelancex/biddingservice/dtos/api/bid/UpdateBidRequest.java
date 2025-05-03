package com.freelancex.biddingservice.dtos.api.bid;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class UpdateBidRequest {
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    Double amount;

    @NotBlank(message = "Proposal is required")
    @Size(max = 500, message = "Proposal should not exceeds 500 characters")
    String proposal;
}
