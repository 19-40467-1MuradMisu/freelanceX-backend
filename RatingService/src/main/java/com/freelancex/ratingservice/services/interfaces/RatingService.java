package com.freelancex.ratingservice.services.interfaces;

import com.freelancex.ratingservice.models.Rating;

import java.util.List;
import java.util.UUID;

public interface RatingService {
    Rating createRating(Rating rating);
    Rating updateRating(UUID ratingId, UUID UserId, Rating newRatingData);
    List<Rating> getRatingsByJobId(UUID jobId);
    List<Rating> getRatingsByUserId(UUID userId);
    Rating getRatingByRatingIdAndUserId(UUID ratingId, UUID userId);
    void deleteRating(UUID jobId, UUID userId);
}
