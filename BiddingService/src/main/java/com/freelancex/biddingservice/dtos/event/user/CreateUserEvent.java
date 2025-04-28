package com.freelancex.biddingservice.dtos.event.user;

import com.freelancex.biddingservice.enums.UserRole;

import java.util.UUID;

public record CreateUserEvent(UUID userId, String firstName, String lastName, UserRole role) {
}
