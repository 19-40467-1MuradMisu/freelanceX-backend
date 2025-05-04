package com.freelancex.ratingservice.services;

import com.freelancex.ratingservice.exceptions.ApiException;
import com.freelancex.ratingservice.models.Rating;
import com.freelancex.ratingservice.repositories.RatingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RatingServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(RatingServiceImpl.class);

    private final RatingRepository ratingRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    // Create a new rating
    public Rating createRating(Rating rating) {
        logger.info("Creating rating for jobId: {}, userId: {}", rating.getJobId(), rating.getUserId());
        try {
            // Ensure new entity state
        
   
            return ratingRepository.save(rating);
        } catch (ObjectOptimisticLockingFailureException e) {
            logger.error("Optimistic locking failure for rating: {}", rating, e);
            throw new ApiException("Rating was updated by another transaction. Please try again.", HttpStatus.CONFLICT);
        } catch (DataIntegrityViolationException e) {
            logger.error("Data integrity violation for rating: {}", rating, e);
            throw new ApiException("Rating already exists for this job and user", HttpStatus.CONFLICT);
        }
    }

    // Update existing rating
    public Rating updateRating(UUID ratingId, Rating newRatingData) {
        logger.info("Updating rating with id: {}", ratingId);
        try {
            Rating existingRating = getRatingsById(ratingId);
            existingRating.setScore(newRatingData.getScore());
            existingRating.setComment(newRatingData.getComment());
            return ratingRepository.save(existingRating);
        } catch (ObjectOptimisticLockingFailureException e) {
            logger.error("Optimistic locking failure for rating id: {}", ratingId, e);
            throw new ApiException("Rating was updated by another transaction. Please try again.", HttpStatus.CONFLICT);
        }
    }

    // Get all ratings
    public List<Rating> getAllRatings() {
        logger.info("Fetching all ratings");
        return ratingRepository.findAll();
    }

    // Get ratings by job ID
    public List<Rating> getRatingsByJobId(UUID jobId) {
        logger.info("Fetching ratings for jobId: {}", jobId);
        return ratingRepository.findAllByJobId(jobId);
    }

    // Get ratings by user ID
    public List<Rating> getRatingsByUserId(UUID userId) {
        logger.info("Fetching ratings for userId: {}", userId);
        return ratingRepository.findAllByUserId(userId);
    }

    // Get rating by ID
    public Rating getRatingsById(UUID id) {
        logger.info("Fetching rating with id: {}", id);
        return ratingRepository.findById(id)
            .orElseThrow(() -> new ApiException("Rating not found", HttpStatus.NOT_FOUND));
    }

    // Get a specific rating by jobId and userId
    public Rating getRatingByJobIdAndUserId(UUID jobId, UUID userId) {
        logger.info("Fetching rating for jobId: {}, userId: {}", jobId, userId);
        return ratingRepository.findByJobIdAndUserId(jobId, userId)
            .orElseThrow(() -> new ApiException("Rating not found", HttpStatus.NOT_FOUND));
    }

    // Delete rating by jobId and userId
    public void deleteRating(UUID jobId, UUID userId) {
        logger.info("Deleting rating for jobId: {}, userId: {}", jobId, userId);
        Rating rating = getRatingByJobIdAndUserId(jobId, userId);
        ratingRepository.delete(rating);
    }
}