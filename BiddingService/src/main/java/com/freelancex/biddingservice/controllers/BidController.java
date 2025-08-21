package com.freelancex.biddingservice.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.freelancex.biddingservice.dtos.api.bid.CreateBidRequest;
import com.freelancex.biddingservice.dtos.api.bid.UpdateBidRequest;
import com.freelancex.biddingservice.dtos.common.ApiResponse;
import com.freelancex.biddingservice.models.Bid;
import com.freelancex.biddingservice.services.interfaces.BidService;
import com.freelancex.biddingservice.views.Views;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bid")
public class BidController {

    private final BidService bidService;

    @Autowired
    public BidController(BidService bidService) {
        this.bidService = bidService;
    }

    @GetMapping("/client/job/{jobId}")
    @JsonView(Views.ClientBidView.class)
    public ResponseEntity<List<Bid>> getBidByJobId(@PathVariable UUID jobId) {
        List<Bid> bids = this.bidService.getBidsByJobId(jobId);
        return ResponseEntity.ok(bids);
    }

    @GetMapping("/freelancer/{freelancerId}")
    @JsonView(Views.FreelancerBidView.class)
    public ResponseEntity<List<Bid>> getBidsByFreelancerId(@PathVariable UUID freelancerId) {
        List<Bid> bids = this.bidService.getBidsByFreelancerId(freelancerId);
        return ResponseEntity.ok(bids);
    }

    @GetMapping("{bidId}/freelancer/{freelancerId}")
    @JsonView(Views.FreelancerBidView.class)
    public ResponseEntity<Bid> getBidByFreelancerId(@PathVariable UUID bidId,
                                                                 @PathVariable UUID freelancerId) {
        Bid bid = this.bidService.getBidByFreelancerId(bidId, freelancerId);
        return ResponseEntity.ok(bid);
    }

    @PostMapping("/freelancer")
    public ResponseEntity<Void> createBid(@RequestBody @Valid CreateBidRequest request) {
        this.bidService.createBid(request);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PatchMapping("/{bidId}/freelancer/{freelancerId}")
    public ResponseEntity<Void> updateBidByFreelancerId(@PathVariable UUID bidId,
                                                               @PathVariable UUID freelancerId,
                                                               @RequestBody @Valid UpdateBidRequest request) {
        this.bidService.updateBidByFreelancerId(bidId, freelancerId, request);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{bidId}/freelancer/{freelancerId}")
    public ResponseEntity<Void> deleteBidByFreelancerId(@PathVariable UUID bidId,
                                                               @PathVariable UUID freelancerId) {
        this.bidService.deleteBidByFreelancerId(bidId, freelancerId);
        return ResponseEntity.ok(null);
    }
}
