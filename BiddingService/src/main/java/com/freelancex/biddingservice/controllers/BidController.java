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
    public ResponseEntity<ApiResponse<List<Bid>>> getBidByJobId(@PathVariable UUID jobId) {
        List<Bid> bids = this.bidService.getBidsByJobId(jobId);

        ApiResponse<List<Bid>> response = new ApiResponse<>("success", 200, bids);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/freelancer/{freelancerId}")
    @JsonView(Views.FreelancerBidView.class)
    public ResponseEntity<ApiResponse<List<Bid>>> getBidsByFreelancerId(@PathVariable UUID freelancerId) {
        List<Bid> bids = this.bidService.getBidsByFreelancerId(freelancerId);

        ApiResponse<List<Bid>> response = new ApiResponse<>("success", 200, bids);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{bidId}/freelancer/{freelancerId}")
    @JsonView(Views.FreelancerBidView.class)
    public ResponseEntity<ApiResponse<Bid>> getBidByFreelancerId(@PathVariable UUID bidId,
                                                                 @PathVariable UUID freelancerId) {
        Bid bid = this.bidService.getBidByFreelancerId(bidId, freelancerId);

        ApiResponse<Bid> response = new ApiResponse<>("success", 200, bid);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/freelancer")
    public ResponseEntity<ApiResponse> createBid(@RequestBody @Valid CreateBidRequest request) {
        this.bidService.createBid(request);

        ApiResponse response = new ApiResponse<>("success", 200, null);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{bidId}/freelancer/{freelancerId}")
    public ResponseEntity<ApiResponse> updateBidByFreelancerId(@PathVariable UUID bidId,
                                                               @PathVariable UUID freelancerId,
                                                               @RequestBody @Valid UpdateBidRequest request) {
        this.bidService.updateBidByFreelancerId(bidId, freelancerId, request);

        ApiResponse response = new ApiResponse<>("success", 200, null);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{bidId}/freelancer/{freelancerId}")
    public ResponseEntity<ApiResponse> deleteBidByFreelancerId(@PathVariable UUID bidId,
                                                               @PathVariable UUID freelancerId) {
        this.bidService.deleteBidByFreelancerId(bidId, freelancerId);

        ApiResponse response = new ApiResponse<>("success", 200, null);
        return ResponseEntity.ok(response);
    }
}
