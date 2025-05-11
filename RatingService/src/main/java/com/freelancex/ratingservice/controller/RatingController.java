package com.freelancex.ratingservice.controller;

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
    public ResponseEntity<Rating> createRating(@RequestBody Rating rating) {
        Rating createdRating = ratingService.createRating(rating);
        return new ResponseEntity<>(createdRating, HttpStatus.CREATED);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<Rating>> getAllRatings() {
        List<Rating> ratings = ratingService.getAllRatings();
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<Rating>> getRatingByJobId(@PathVariable("jobId") UUID jobId) {
        List<Rating> ratings = ratingService.getRatingsByJobId(jobId);
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Rating>> getRatingsByUserId(@PathVariable("userId") UUID userId) {
        List<Rating> ratings = ratingService.getRatingsByUserId(userId);
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }

    @PutMapping("/{id}/user/{userId}")
    public ResponseEntity<Rating> updateRating(@PathVariable UUID id,
                                                            @PathVariable UUID userId,
                                                            @RequestBody Rating ratingDetails) {
        Rating updatedRating = ratingService.updateRating(id, userId, ratingDetails);
        return new ResponseEntity<>(updatedRating, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/user/{userId}")
    public ResponseEntity<Void> deleteRating(@PathVariable UUID id, @PathVariable UUID userId) {
        ratingService.deleteRating(id,userId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
