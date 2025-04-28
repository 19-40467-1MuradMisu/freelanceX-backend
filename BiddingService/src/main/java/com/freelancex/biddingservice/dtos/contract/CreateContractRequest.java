package com.freelancex.biddingservice.dtos.contract;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateContractRequest {

    @NotNull(message = "Bid ID is required")
    private UUID bidId;

    @NotBlank(message = "Terms is required")
    @Size(max = 500, message = "Terms should not exceed 500 characters")
    private String terms;
}
