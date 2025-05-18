package com.freelancex.ratingservice.services.impl;

import com.freelancex.ratingservice.dtos.event.rating.CreateRatingEvent;
import com.freelancex.ratingservice.exceptions.ApiException;
import com.freelancex.ratingservice.kafka.KafkaProducerService;
import com.freelancex.ratingservice.models.Rating;
import com.freelancex.ratingservice.repositories.RatingRepository;
import com.freelancex.ratingservice.services.interfaces.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final KafkaProducerService kafkaProducerService;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository, KafkaProducerService kafkaProducerService) {
        this.ratingRepository = ratingRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    public Rating createRating(Rating rating) {
        Optional<Rating> ratingOptional = ratingRepository.findByJobIdAndUserId(
                rating.getJobId(), rating.getUserId());

        if (ratingOptional.isPresent()) {
            throw new ApiException("Rating already exists", HttpStatus.CONFLICT);
        }

        Rating savedRating = ratingRepository.save(rating);

        CreateRatingEvent event = new CreateRatingEvent(rating.getUserId(), rating.getScore());
        kafkaProducerService.sendRatingCreatedEvent(event);

        return savedRating;
    }

    @Override
    public Rating updateRating(UUID ratingId, UUID UserId, Rating newRatingData) {
        Rating existingRating = getRatingByRatingIdAndUserId(ratingId, UserId);
        existingRating.setScore(newRatingData.getScore());
        existingRating.setComment(newRatingData.getComment());
        return ratingRepository.save(existingRating);
    }

    @Override
    public List<Rating> getRatingsByJobId(UUID jobId) {
        return ratingRepository.findAllByJobId(jobId);
    }

    @Override
    public List<Rating> getRatingsByUserId(UUID userId) {
        return ratingRepository.findAllByUserId(userId);
    }

    @Override
    public Rating getRatingByRatingIdAndUserId(UUID ratingId, UUID userId) {
        return ratingRepository.findByRatingIdAndUserId(ratingId, userId)
                .orElseThrow(() -> new ApiException("Rating not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public void deleteRating(UUID jobId, UUID userId) {
        Rating rating = getRatingByRatingIdAndUserId(jobId, userId);
        ratingRepository.delete(rating);
    }
}
