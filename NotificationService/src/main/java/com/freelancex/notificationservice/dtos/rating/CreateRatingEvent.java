package com.freelancex.notificationservice.dtos.rating;

import com.freelancex.notificationservice.enums.Score;

import java.util.UUID;

public record CreateRatingEvent(UUID userId, Score score) {
}
