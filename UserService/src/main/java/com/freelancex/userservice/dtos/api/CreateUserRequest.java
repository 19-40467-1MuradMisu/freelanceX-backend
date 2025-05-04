package com.freelancex.userservice.dtos.api;

import com.freelancex.userservice.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank String firstName, @NotBlank String lastName, @NotBlank @Email String email,
        @NotBlank @Size(min = 8) String password, @NotBlank UserRole role) {
}
