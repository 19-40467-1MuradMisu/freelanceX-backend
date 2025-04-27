package com.freelancex.biddingservice.services;

import com.freelancex.biddingservice.models.Bid;
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
    public List<Bid> getAllBids() {
        return bidRepository.findAll();
    }

    @Override
    public Optional<Bid> getBidById(UUID id) {
        return bidRepository.findById(id);
    }

    @Override
    public Bid saveBid(Bid bid) {
        return bidRepository.save(bid);
    }

    @Override
    public void deleteBid(UUID id) {
        bidRepository.deleteById(id);
    }
}
