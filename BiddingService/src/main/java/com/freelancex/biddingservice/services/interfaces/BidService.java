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

    List<Bid> getBidsByUserId(UUID userId);

    Bid getBidByUserId(UUID bidId, UUID userId) throws ApiException;

    void updateBidByUserId(UUID bidId, UUID userId, UpdateBidRequest request) throws ApiException;

    void deleteBidByUserId(UUID bidId, UUID userId) throws ApiException;
}
