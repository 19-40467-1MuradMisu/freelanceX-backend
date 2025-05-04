package com.freelancex.ratingservice.dtos.common;

import lombok.Builder;

@Builder
public record ApiResponse<T>(String message, int statusCode, T data) {
}
