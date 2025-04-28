package com.freelancex.biddingservice.dtos.contract;

import com.freelancex.biddingservice.dtos.common.Response;
import com.freelancex.biddingservice.models.Bid;
import com.freelancex.biddingservice.models.Contract;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class GetContractsResponse extends Response {

    @Getter
    private List<Contract> bids;
}
