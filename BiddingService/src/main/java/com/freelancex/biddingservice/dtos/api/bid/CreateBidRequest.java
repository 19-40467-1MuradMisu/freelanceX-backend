package com.freelancex.biddingservice.dtos.api.bid;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.util.UUID;

@Value
public class CreateBidRequest {

    @NotNull(message = "Job ID is required")
    UUID jobId;

    @NotNull(message = "Freelancer ID is required")
    UUID freelancerId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    Double amount;

    @NotBlank(message = "Proposal is required")
    @Size(max = 1000, message = "Proposal should not exceed 1000 characters")
    String proposal;
}
