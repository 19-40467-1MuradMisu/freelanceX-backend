package com.freelancex.biddingservice.controllers;

import com.freelancex.biddingservice.dtos.bid.*;
import com.freelancex.biddingservice.services.interfaces.BidService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/bid")
public class BidController {

    private final BidService bidService;

    @Autowired
    public BidController(BidService bidService) {
        this.bidService = bidService;
    }

    @GetMapping()
    public ResponseEntity<GetBidsResponse> getBids() {
        GetBidsResponse response = this.bidService.getAllBids();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetBidResponse> getBidById(@PathVariable UUID id) {
        GetBidResponse response = this.bidService.getBidById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<CreateBidResponse> createBid(@RequestBody @Valid CreateBidRequest request) {
        CreateBidResponse response = this.bidService.createBid(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UpdateBidResponse> updateBid(@PathVariable UUID id, @RequestBody @Valid UpdateBidRequest request) {
        UpdateBidResponse response = this.bidService.updateBid(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteBidResponse> deleteBid(@PathVariable UUID id) {
        DeleteBidResponse response = this.bidService.deleteBid(id);
        return ResponseEntity.ok(response);
    }
}
