package com.freelancex.userservice.dtos.api;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(@NotBlank String firstName, @NotBlank String lastName, @NotBlank String bio) {
}
