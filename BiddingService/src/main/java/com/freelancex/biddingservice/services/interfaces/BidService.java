package com.freelancex.biddingservice.services.interfaces;

import com.freelancex.biddingservice.dtos.api.bid.CreateBidRequest;
import com.freelancex.biddingservice.dtos.api.bid.UpdateBidRequest;
import com.freelancex.biddingservice.exceptions.ApiException;
import com.freelancex.biddingservice.models.Bid;

import java.util.List;
import java.util.UUID;

public interface BidService {

    void createBid(CreateBidRequest request) throws ApiException;

    List<Bid> getBidsByJobId(UUID jobId);

    List<Bid> getBidsByFreelancerId(UUID freelancerId);

    Bid getBidByFreelancerId(UUID bidId, UUID freelancerId) throws ApiException;

    void updateBidByFreelancerId(UUID bidId, UUID freelancerId, UpdateBidRequest request) throws ApiException;

    void deleteBidByFreelancerId(UUID bidId, UUID freelancerId) throws ApiException;
}
