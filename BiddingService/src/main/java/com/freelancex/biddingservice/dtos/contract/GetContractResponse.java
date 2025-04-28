package com.freelancex.biddingservice.dtos.contract;

import com.freelancex.biddingservice.dtos.common.Response;
import com.freelancex.biddingservice.models.Bid;
import com.freelancex.biddingservice.models.Contract;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class GetContractResponse extends Response {

    @Getter
    private Contract data;
}
