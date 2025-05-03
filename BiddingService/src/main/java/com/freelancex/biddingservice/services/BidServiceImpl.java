package com.freelancex.biddingservice.services;

import com.freelancex.biddingservice.dtos.api.bid.CreateBidRequest;
import com.freelancex.biddingservice.dtos.api.bid.UpdateBidRequest;
import com.freelancex.biddingservice.exceptions.ApiException;
import com.freelancex.biddingservice.models.Bid;
import com.freelancex.biddingservice.repositories.BidRepository;
import com.freelancex.biddingservice.services.interfaces.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BidServiceImpl implements BidService {
    private final BidRepository bidRepository;

    @Autowired
    public BidServiceImpl(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }

    @Override
    public Bid getBidById(UUID bidId) {
        Optional<Bid> bid = bidRepository.findById(bidId);

        if (bid.isEmpty()) {
            throw new ApiException("Bid not found", HttpStatus.NOT_FOUND);
        }

        return bid.get();
    }

    @Override
    public List<Bid> getBidsByJobId(UUID jobId) {
        return this.bidRepository.findByJobId(jobId);
    }

    @Override
    public void createBid(CreateBidRequest request) throws ApiException {
        Optional<Bid> bidOptional = this.bidRepository.findByFreelancerIdAndJobId(request.getFreelancerId(),
                request.getJobId());

        if (bidOptional.isPresent()) {
            throw new ApiException("Bid already exists for this job", HttpStatus.CONFLICT);
        }

        Bid bid = new Bid();
        bid.setFreelancerId(request.getFreelancerId());
        bid.setJobId(request.getJobId());
        bid.setAmount(request.getAmount());
        bid.setProposal(request.getProposal());
        bidRepository.save(bid);
    }

    @Override
    public Bid getBidByFreelancerId(UUID bidId, UUID freelancerId) throws ApiException {
        Optional<Bid> bid = bidRepository.findByBidIdAndFreelancerId(bidId, freelancerId);

        if (bid.isEmpty()) {
            throw new ApiException("Bid not found", HttpStatus.NOT_FOUND);
        }

        return bid.get();
    }

    @Override
    public List<Bid> getBidsByFreelancerId(UUID freelancerId) {

        return this.bidRepository.findByFreelancerId(freelancerId);
    }

    @Override
    public void updateBidByFreelancerId(UUID bidId, UUID freelancerId,
                                               UpdateBidRequest request) throws ApiException {
        Optional<Bid> bid = bidRepository.findByBidIdAndFreelancerId(bidId, freelancerId);

        if (bid.isEmpty()) {
            throw new ApiException("Bid not found", HttpStatus.NOT_FOUND);
        }

        Bid bidToUpdate = bid.get();
        bidToUpdate.setAmount(request.getAmount());
        bidToUpdate.setProposal(request.getProposal());
        bidRepository.save(bidToUpdate);
    }

    @Override
    public void deleteBidByFreelancerId(UUID bidId, UUID freelancerId) throws ApiException {
        Optional<Bid> optionalBid = bidRepository.findByBidIdAndFreelancerId(bidId, freelancerId);

        if (optionalBid.isEmpty()) {
            throw new ApiException("Bid not found", HttpStatus.NOT_FOUND);
        }

        Bid bid = optionalBid.get();

        if (bid.getContract() != null) {
            throw new ApiException("A contract already exists for this bid", HttpStatus.FORBIDDEN);
        }

        bidRepository.delete(bid);
    }
}
