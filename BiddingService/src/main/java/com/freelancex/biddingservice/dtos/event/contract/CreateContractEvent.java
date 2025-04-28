package com.freelancex.biddingservice.dtos.event.contract;

import com.freelancex.biddingservice.enums.ContractStatus;
import lombok.Value;

import java.util.UUID;

@Value
public class CreateContractEvent {
    UUID contractId;
    ContractStatus status;
}
