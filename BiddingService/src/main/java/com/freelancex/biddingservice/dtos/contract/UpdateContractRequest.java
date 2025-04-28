package com.freelancex.biddingservice.dtos.contract;

import com.freelancex.biddingservice.enums.ContractStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateContractRequest {
    @NotBlank(message = "Terms is required")
    @Size(max = 500, message = "Terms should not exceed 500 characters")
    private String terms;

    @NotBlank(message = "Contract status is required")
    private ContractStatus status;
}
