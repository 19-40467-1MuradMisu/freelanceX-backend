package com.freelancex.biddingservice.services.interfaces;

import com.freelancex.biddingservice.dtos.api.bid.*;
import com.freelancex.biddingservice.exceptions.ApiException;

import java.util.UUID;

public interface BidService {
    GetBidsResponse getAllBids();

    GetBidResponse getBidById(UUID id) throws ApiException;

    CreateBidResponse createBid(CreateBidRequest request);

    UpdateBidResponse updateBid(UUID id, UpdateBidRequest request) throws ApiException;

    DeleteBidResponse deleteBid(UUID id);
}
