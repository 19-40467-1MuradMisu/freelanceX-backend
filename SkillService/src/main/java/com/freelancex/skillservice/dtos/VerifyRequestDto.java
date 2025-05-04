package com.freelancex.skillservice.dtos;

import lombok.Data;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class VerifyRequestDto {
    @NotNull
    private UUID userId;

    @NotBlank
    private String skills;
}
