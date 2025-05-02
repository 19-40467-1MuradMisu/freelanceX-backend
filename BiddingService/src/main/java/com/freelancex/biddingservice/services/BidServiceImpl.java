package com.freelancex.biddingservice.services;

import com.freelancex.biddingservice.dtos.api.bid.CreateBidRequest;
import com.freelancex.biddingservice.dtos.api.bid.UpdateBidRequest;
import com.freelancex.biddingservice.exceptions.ApiException;
import com.freelancex.biddingservice.models.Bid;
import com.freelancex.biddingservice.models.Job;
import com.freelancex.biddingservice.repositories.BidRepository;
import com.freelancex.biddingservice.repositories.ContractRepository;
import com.freelancex.biddingservice.repositories.JobRepository;
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
    private final ContractRepository contractRepository;
    private final JobRepository jobRepository;

    @Autowired
    public BidServiceImpl(BidRepository bidRepository, ContractRepository contractRepository,
                          JobRepository jobRepository) {
        this.bidRepository = bidRepository;
        this.contractRepository = contractRepository;
        this.jobRepository = jobRepository;
    }

    @Override
    public List<Bid> getBidsByJobId(UUID jobId) {

        return this.bidRepository.findBidsByJobId(jobId);
    }

    @Override
    public void createBid(CreateBidRequest request) throws ApiException {
        Optional<Bid> bidOptional = this.bidRepository.findByUserIdAndJobId(request.getUserId(),
                request.getJobId());

        if (bidOptional.isPresent()) {
            throw new ApiException("Bid already exists for this job", HttpStatus.CONFLICT);
        }

        Optional<Job> jobOptional = this.jobRepository.findById(request.getJobId());

        if (jobOptional.isEmpty()) {
            throw new ApiException("Job does not exists", HttpStatus.NOT_FOUND);
        }

        Job job = jobOptional.get();

        if (request.getAmount() > job.getBudget()) {
            throw new ApiException("Amount exceeds budget", HttpStatus.BAD_REQUEST);
        }

        Bid bid = new Bid();
        bid.setUserId(request.getUserId());
        bid.setJobId(request.getJobId());
        bid.setAmount(request.getAmount());
        bid.setProposal(request.getProposal());
        bidRepository.save(bid);
    }

    @Override
    public Bid getBidByUserId(UUID bidId, UUID userId) throws ApiException {
        Optional<Bid> bid = bidRepository.findByBidIdAndUserId(bidId, userId);

        if (bid.isEmpty()) {
            throw new ApiException("Bid not found", HttpStatus.NOT_FOUND);
        }

        return bid.get();
    }

    @Override
    public List<Bid> getBidsByUserId(UUID userId) {

        return this.bidRepository.findBidsByUserId(userId);
    }

    @Override
    public void updateBidByUserId(UUID bidId, UUID userId,
                                               UpdateBidRequest request) throws ApiException {
        Optional<Bid> bid = bidRepository.findByBidIdAndUserId(bidId, userId);

        if (bid.isEmpty()) {
            throw new ApiException("Bid not found", HttpStatus.NOT_FOUND);
        }

        Bid bidToUpdate = bid.get();
        bidToUpdate.setAmount(request.getAmount());
        bidToUpdate.setProposal(request.getProposal());
        bidRepository.save(bidToUpdate);
    }

    @Override
    public void deleteBidByUserId(UUID bidId, UUID userId) throws ApiException {
        Optional<Bid> bid = bidRepository.findByBidIdAndUserId(bidId, userId);

        if (bid.isEmpty()) {
            throw new ApiException("Bid not found", HttpStatus.NOT_FOUND);
        }

        boolean contractExists = contractRepository.existsContractByBidId(bidId);

        if (contractExists) {
            throw new ApiException("A contract already exists for this bid", HttpStatus.FORBIDDEN);
        }

        bidRepository.delete(bid.get());
    }
}
