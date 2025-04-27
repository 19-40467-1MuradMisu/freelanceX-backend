package com.freelancex.biddingservice.repositories;

import com.freelancex.biddingservice.models.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BidRepository extends JpaRepository<Bid, UUID> {
}
