package com.freelancex.ratingservice.dtos.event.rating;

import com.freelancex.ratingservice.enums.Score;

import java.util.UUID;

public record CreateRatingEvent(UUID userId, Score score) {
}
