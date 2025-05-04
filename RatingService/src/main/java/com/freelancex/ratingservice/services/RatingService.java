package com.freelancex.ratingservice.services;

import com.freelancex.ratingservice.dtos.event.rating.CreateRatingEvent;
import com.freelancex.ratingservice.exceptions.ApiException;
import com.freelancex.ratingservice.kafka.KafkaProducerService;
import com.freelancex.ratingservice.models.Rating;
import com.freelancex.ratingservice.repositories.RatingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RatingService {

    private static final Logger logger = LoggerFactory.getLogger(RatingService.class);

    private final RatingRepository ratingRepository;
    private final KafkaProducerService kafkaProducerService;

    @Autowired
    public RatingService(RatingRepository ratingRepository, KafkaProducerService kafkaProducerService) {
        this.ratingRepository = ratingRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    public Rating createRating(Rating rating) {
        Optional<Rating> ratingOptional = ratingRepository.findByJobIdAndUserId(rating.getJobId(),
                rating.getUserId());

        if (ratingOptional.isPresent()) {
            throw new ApiException("Rating already exists", HttpStatus.CONFLICT);
        }

        Rating savedRating = ratingRepository.save(rating);

        CreateRatingEvent event = new CreateRatingEvent(rating.getUserId(), rating.getScore());
        kafkaProducerService.sendRatingCreatedEvent(event);

        return savedRating;
    }

    public Rating updateRating(UUID ratingId, Rating newRatingData) {

        Rating existingRating = getRatingsById(ratingId);
        existingRating.setScore(newRatingData.getScore());
        existingRating.setComment(newRatingData.getComment());
        return ratingRepository.save(existingRating);
    }

    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    public List<Rating> getRatingsByJobId(UUID jobId) {
        logger.info("Fetching ratings for jobId: {}", jobId);
        return ratingRepository.findAllByJobId(jobId);
    }

    public List<Rating> getRatingsByUserId(UUID userId) {
        return ratingRepository.findAllByUserId(userId);
    }

    public Rating getRatingsById(UUID id) {
        return ratingRepository.findById(id)
            .orElseThrow(() -> new ApiException("Rating not found", HttpStatus.NOT_FOUND));
    }

    public Rating getRatingByJobIdAndUserId(UUID jobId, UUID userId) {
        return ratingRepository.findByJobIdAndUserId(jobId, userId)
            .orElseThrow(() -> new ApiException("Rating not found", HttpStatus.NOT_FOUND));
    }

    public void deleteRating(UUID jobId, UUID userId) {
        Rating rating = getRatingByJobIdAndUserId(jobId, userId);
        ratingRepository.delete(rating);
    }
}