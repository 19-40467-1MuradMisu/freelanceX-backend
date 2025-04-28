package com.freelancex.biddingservice.services;

import com.freelancex.biddingservice.dtos.bid.*;
import com.freelancex.biddingservice.exceptions.ApiException;
import com.freelancex.biddingservice.models.Bid;
import com.freelancex.biddingservice.models.Job;
import com.freelancex.biddingservice.models.User;
import com.freelancex.biddingservice.repositories.BidRepository;
import com.freelancex.biddingservice.services.interfaces.BidService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BidServiceImpl implements BidService {
    private final BidRepository bidRepository;
    private final EntityManager entityManager;

    @Autowired
    public BidServiceImpl(BidRepository bidRepository, EntityManager entityManager) {
        this.bidRepository = bidRepository;
        this.entityManager = entityManager;
    }

    @Override
    public GetBidsResponse getAllBids() {
        List<Bid> bids = bidRepository.findAll();

        GetBidsResponse response = new GetBidsResponse(bids);
        response.setMessage("success");
        response.setStatusCode(200);
        return response;
    }

    @Override
    public GetBidResponse getBidById(UUID id) throws ApiException {
        Optional<Bid> bid = bidRepository.findById(id);

        if (bid.isEmpty()) {
            throw new ApiException(String.format("Bid:%s not found", id), HttpStatus.NOT_FOUND);
        }

        GetBidResponse response = new GetBidResponse(bid.get());
        response.setMessage("success");
        response.setStatusCode(200);
        return response;
    }

    @Override
    public CreateBidResponse createBid(CreateBidRequest request) {
        Bid bid = new Bid();

        bid.setJob(entityManager.getReference(Job.class, request.getJobId()));
        bid.setFreelancer(entityManager.getReference(User.class, request.getFreelancerId()));
        bid.setAmount(request.getAmount());
        bid.setProposal(request.getProposal());

        bidRepository.save(bid);

        CreateBidResponse response = new CreateBidResponse();
        response.setMessage("success");
        response.setStatusCode(200);
        return response;
    }

    @Override
    public UpdateBidResponse updateBid(UUID id, UpdateBidRequest request) throws ApiException {
        Optional<Bid> bid = bidRepository.findById(id);
        if (bid.isEmpty()) {
            throw new ApiException(String.format("Bid:%s not found", id), HttpStatus.NOT_FOUND);
        }

        Bid bidToUpdate = bid.get();
        bidToUpdate.setAmount(request.getAmount());
        bidToUpdate.setProposal(request.getProposal());
        bidRepository.save(bidToUpdate);

        UpdateBidResponse response = new UpdateBidResponse();
        response.setMessage("success");
        response.setStatusCode(200);
        return response;
    }

    @Override
    public DeleteBidResponse deleteBid(UUID id) {
        Optional<Bid> bid = bidRepository.findById(id);
        if (bid.isEmpty()) {
            throw new ApiException(String.format("Bid:%s not found", id), HttpStatus.NOT_FOUND);
        }

        bidRepository.deleteById(bid.get().getBidId());

        DeleteBidResponse response = new DeleteBidResponse();
        response.setMessage("success");
        response.setStatusCode(200);
        return response;
    }
}
