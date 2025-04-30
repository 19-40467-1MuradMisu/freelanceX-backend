package com.freelancex.biddingservice.services.interfaces;

import com.freelancex.biddingservice.dtos.api.bid.*;
import com.freelancex.biddingservice.exceptions.ApiException;

import java.util.UUID;

public interface BidService {

    CreateBidResponse createBid(CreateBidRequest request) throws ApiException;

    GetBidsResponse getBidsByJobId(UUID jobId);

    GetBidsResponse getBidsByUserId(UUID userId);

    GetBidResponse getBidByUserId(UUID bidId, UUID userId) throws ApiException;

    UpdateBidResponse updateBidByUserId(UUID bidId, UUID userId, UpdateBidRequest request) throws ApiException;

    DeleteBidResponse deleteBidByUserId(UUID bidId, UUID userId) throws ApiException;
}
