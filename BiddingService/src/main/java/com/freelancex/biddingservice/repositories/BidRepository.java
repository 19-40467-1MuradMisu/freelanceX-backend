package com.freelancex.biddingservice.repositories;

import com.freelancex.biddingservice.models.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BidRepository extends JpaRepository<Bid, UUID> {

    List<Bid> findByJobId(UUID jobId);

    List<Bid> findByFreelancerId(UUID freelancerId);

    Optional<Bid> findByBidIdAndFreelancerId(UUID bidId, UUID freelancerId);

    Optional<Bid> findByFreelancerIdAndJobId(UUID freelancerId, UUID jobId);
}
