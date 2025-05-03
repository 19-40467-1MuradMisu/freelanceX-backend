package com.freelancex.paymentservice.dtos.common;

import lombok.Builder;

@Builder
public record ApiResponse<T>(String message, int statusCode, T data) {
}
