package com.freelancex.biddingservice.dtos.event.user;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DeleteUserEvent(@NotNull UUID userId) {
}
