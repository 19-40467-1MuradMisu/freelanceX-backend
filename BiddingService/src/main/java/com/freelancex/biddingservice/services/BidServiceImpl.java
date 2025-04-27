package com.freelancex.biddingservice.services;

import com.freelancex.biddingservice.dtos.bid.*;
import com.freelancex.biddingservice.exceptions.NotFoundException;
import com.freelancex.biddingservice.models.Bid;
import com.freelancex.biddingservice.models.Job;
import com.freelancex.biddingservice.models.User;
import com.freelancex.biddingservice.repositories.BidRepository;
import com.freelancex.biddingservice.services.interfaces.BidService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public GetBidsResponse getAllBids() {
        List<Bid> bids = bidRepository.findAll();

        GetBidsResponse response = new GetBidsResponse(bids);
        response.setMessage("success");
        response.setStatusCode(200);
        return response;
    }

    @Override
    public GetBidResponse getBidById(UUID id) throws NotFoundException {
        Optional<Bid> bid = bidRepository.findById(id);

        if (bid.isEmpty()) {
            throw new NotFoundException("Bid not found");
        }

        GetBidResponse response = new GetBidResponse(bid.get());
        response.setMessage("success");
        response.setStatusCode(200);
        return response;
    }

    @Override
    public CreateBidResponse saveBid(CreateBidRequest request) {
        Bid bid = new Bid();

        Job job = new Job();
        job.setJobId(request.getJobId());

        User freelancer = new User();
        freelancer.setUserId(request.getFreelancerId());

        bid.setJob(job);
        bid.setFreelancer(freelancer);
        bid.setAmount(request.getAmount());
        bid.setProposal(request.getProposal());

        bidRepository.save(bid);

        CreateBidResponse response = new CreateBidResponse();
        response.setMessage("success");
        response.setStatusCode(200);
        return response;
    }

    @Override
    public UpdateBidResponse updateBid(UUID id, UpdateBidRequest request) throws NotFoundException {
        Optional<Bid> bid = bidRepository.findById(id);
        if (bid.isEmpty()) {
            throw new NotFoundException("Bid not found");
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

        bidRepository.deleteById(id);

        DeleteBidResponse response = new DeleteBidResponse();
        response.setMessage("success");
        response.setStatusCode(200);
        return response;
    }
}
