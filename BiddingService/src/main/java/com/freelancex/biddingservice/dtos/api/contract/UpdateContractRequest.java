package com.freelancex.biddingservice.dtos.api.contract;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class UpdateContractRequest {
    @NotBlank(message = "Terms is required")
    @Size(max = 500, message = "Terms should not exceed 500 characters")
    String terms;
}
