package com.freelancex.biddingservice.dtos.bid;

import com.freelancex.biddingservice.dtos.common.Response;
import com.freelancex.biddingservice.models.Bid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetBidResponse extends Response {

    private Bid data;
}
