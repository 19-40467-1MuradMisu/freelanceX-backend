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
@RequestMapping("/ratings")
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

    @GetMapping
    public ResponseEntity<ApiResponse<List<Rating>>> getAllRatings() {
        List<Rating> ratings = ratingService.getAllRatings();
        ApiResponse<List<Rating>> response = new ApiResponse<>("Ratings fetched successfully", HttpStatus.OK.value(), ratings);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<ApiResponse<List<Rating>>> getRatingByJobId(@RequestParam("jobId") UUID jobId) {
        List<Rating> ratings = ratingService.getRatingsByJobId(jobId);
        ApiResponse<List<Rating>> response = new ApiResponse<>("Ratings fetched successfully", HttpStatus.OK.value(), ratings);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Rating>>> getRatingByUserId(@RequestParam("userId") UUID userId) {
        List<Rating> ratings = ratingService.getRatingsByUserId(userId);
        ApiResponse<List<Rating>> response = new ApiResponse<>("Ratings fetched successfully", HttpStatus.OK.value(), ratings);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/job/{jobId}/user/{userId}")
    public ResponseEntity<ApiResponse<Rating>> getRatingByJobIdAndUserId(@PathVariable UUID jobId, @PathVariable UUID userId) {
        Rating rating = ratingService.getRatingByJobIdAndUserId(jobId, userId);
        ApiResponse<Rating> response = new ApiResponse<>("Rating fetched successfully", HttpStatus.OK.value(), rating);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Rating>> updateRating(@PathVariable UUID id,
                                                            @RequestBody Rating ratingDetails) {
        Rating updatedRating = ratingService.updateRating(id, ratingDetails);
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
