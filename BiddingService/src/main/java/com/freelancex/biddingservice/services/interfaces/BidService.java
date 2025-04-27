package com.freelancex.biddingservice.services.interfaces;

import com.freelancex.biddingservice.models.Bid;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BidService {
    List<Bid> getAllBids();

    Optional<Bid> getBidById(UUID id);

    Bid saveBid(Bid bid);

    void deleteBid(UUID id);
}
