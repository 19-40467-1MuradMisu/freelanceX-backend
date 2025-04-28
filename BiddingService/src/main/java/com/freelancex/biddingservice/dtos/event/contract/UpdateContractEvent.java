package com.freelancex.biddingservice.dtos.event.contract;

import com.freelancex.biddingservice.enums.ContractStatus;
import lombok.Value;

import java.util.UUID;

@Value
public class UpdateContractEvent {
    UUID contractId;
    ContractStatus status;
}
