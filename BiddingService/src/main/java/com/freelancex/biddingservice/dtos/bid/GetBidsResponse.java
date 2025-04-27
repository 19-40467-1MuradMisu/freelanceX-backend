package com.freelancex.biddingservice.dtos.bid;

import com.freelancex.biddingservice.dtos.common.Response;
import com.freelancex.biddingservice.models.Bid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetBidsResponse extends Response {

    private List<Bid> bids;
}
