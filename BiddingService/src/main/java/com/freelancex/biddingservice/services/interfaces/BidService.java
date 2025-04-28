package com.freelancex.biddingservice.services.interfaces;

import com.freelancex.biddingservice.dtos.bid.*;
import com.freelancex.biddingservice.exceptions.ApiException;

import java.util.UUID;

public interface BidService {
    GetBidsResponse getAllBids();

    GetBidResponse getBidById(UUID id) throws ApiException;

    CreateBidResponse createBid(CreateBidRequest bid);

    UpdateBidResponse updateBid(UUID id, UpdateBidRequest updateBidRequest) throws ApiException;

    DeleteBidResponse deleteBid(UUID id);
}
