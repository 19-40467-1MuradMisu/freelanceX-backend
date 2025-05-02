package com.freelancex.biddingservice.controllers;

import com.freelancex.biddingservice.dtos.api.bid.CreateBidRequest;
import com.freelancex.biddingservice.dtos.api.bid.UpdateBidRequest;
import com.freelancex.biddingservice.dtos.common.ApiResponse;
import com.freelancex.biddingservice.models.Bid;
import com.freelancex.biddingservice.services.interfaces.BidService;
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

    @GetMapping("/job/{jobId}")
    public ResponseEntity<ApiResponse<List<Bid>>> getBidByJobId(@PathVariable UUID jobId) {
        List<Bid> bids = this.bidService.getBidsByJobId(jobId);

        ApiResponse<List<Bid>> response = new ApiResponse<>("success", 200, bids);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Bid>>> getBidsByUserId(@PathVariable UUID userId) {
        List<Bid> bids = this.bidService.getBidsByUserId(userId);

        ApiResponse<List<Bid>> response = new ApiResponse<>("success", 200, bids);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{bidId}/user/{userId}")
    public ResponseEntity<ApiResponse<Bid>> getBidByUserId(@PathVariable UUID bidId,
                                                         @PathVariable UUID userId) {
        Bid bid = this.bidService.getBidByUserId(bidId, userId);

        ApiResponse<Bid> response = new ApiResponse<>("success", 200, bid);
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> createBid(@RequestBody @Valid CreateBidRequest request) {
        this.bidService.createBid(request);

        ApiResponse response = new ApiResponse<>("success", 200, null);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{bidId}/user/{userId}")
    public ResponseEntity<ApiResponse> updateBidByUserId(@PathVariable UUID bidId,
                                                               @PathVariable UUID userId,
                                                               @RequestBody @Valid UpdateBidRequest request) {
        this.bidService.updateBidByUserId(bidId, userId, request);

        ApiResponse response = new ApiResponse<>("success", 200, null);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{bidId}/user/{userId}")
    public ResponseEntity<ApiResponse> deleteBidByUserId(@PathVariable UUID bidId,
                                                               @PathVariable UUID userId) {
        this.bidService.deleteBidByUserId(bidId, userId);

        ApiResponse response = new ApiResponse<>("success", 200, null);
        return ResponseEntity.ok(response);
    }
}
