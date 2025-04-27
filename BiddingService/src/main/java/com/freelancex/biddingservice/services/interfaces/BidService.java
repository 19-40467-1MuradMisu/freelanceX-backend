package com.freelancex.biddingservice.services.interfaces;

import com.freelancex.biddingservice.dtos.bid.*;
import com.freelancex.biddingservice.exceptions.NotFoundException;

import java.util.UUID;

public interface BidService {
    GetBidsResponse getAllBids();

    GetBidResponse getBidById(UUID id) throws NotFoundException;

    CreateBidResponse saveBid(CreateBidRequest bid);

    UpdateBidResponse updateBid(UUID id, UpdateBidRequest updateBidRequest) throws NotFoundException;

    DeleteBidResponse deleteBid(UUID id);
}
