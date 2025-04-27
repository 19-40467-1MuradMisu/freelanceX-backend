package com.freelancex.biddingservice.dtos.bid;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBidRequest {

    @NotNull(message = "Job ID is required")
    private UUID jobId;

    @NotNull(message = "Freelancer ID is required")
    private UUID freelancerId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private Double amount;

    @NotBlank(message = "Proposal is required")
    @Size(max = 500, message = "Proposal should not exceed 500 characters")
    private String proposal;
}
