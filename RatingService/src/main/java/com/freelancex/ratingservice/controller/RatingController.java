package com.freelancex.ratingservice.controller;

import com.freelancex.ratingservice.dtos.common.ApiResponse;
import com.freelancex.ratingservice.models.Rating;
import com.freelancex.ratingservice.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rating")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Rating>> createRating(@RequestBody Rating rating) {
        Rating createdRating = ratingService.createRating(rating);
        ApiResponse<Rating> response = new ApiResponse<>("Rating created successfully", HttpStatus.CREATED.value(), createdRating);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/admin")
    public ResponseEntity<ApiResponse<List<Rating>>> getAllRatings() {
        List<Rating> ratings = ratingService.getAllRatings();
        ApiResponse<List<Rating>> response = new ApiResponse<>("Ratings fetched successfully", HttpStatus.OK.value(), ratings);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<ApiResponse<List<Rating>>> getRatingByJobId(@PathVariable("jobId") UUID jobId) {
        List<Rating> ratings = ratingService.getRatingsByJobId(jobId);
        ApiResponse<List<Rating>> response = new ApiResponse<>("Ratings fetched successfully", HttpStatus.OK.value(), ratings);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Rating>>> getRatingsByUserId(@PathVariable("userId") UUID userId) {
        List<Rating> ratings = ratingService.getRatingsByUserId(userId);
        ApiResponse<List<Rating>> response = new ApiResponse<>("Ratings fetched successfully", HttpStatus.OK.value(), ratings);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}/user/{userId}")
    public ResponseEntity<ApiResponse<Rating>> updateRating(@PathVariable UUID id,
                                                            @PathVariable UUID userId,
                                                            @RequestBody Rating ratingDetails) {
        Rating updatedRating = ratingService.updateRating(id, userId, ratingDetails);
        ApiResponse<Rating> response = new ApiResponse<>("Rating updated successfully", HttpStatus.OK.value(), updatedRating);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/user/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteRating(@PathVariable UUID id, @PathVariable UUID userId) {
        ratingService.deleteRating(id,userId);
        ApiResponse<Void> response = new ApiResponse<>("Rating deleted successfully", HttpStatus.OK.value(), null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
