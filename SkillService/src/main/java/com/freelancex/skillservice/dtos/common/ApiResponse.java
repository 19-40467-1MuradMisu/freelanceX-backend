package com.freelancex.skillservice.dtos.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generic wrapper for API responses.
 *
 * @param <T> the type of the response payload
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    /**
     * A short status message, e.g. "success" or "error".
     */
    private String status;

    /**
     * HTTP status code, e.g. 200, 404.
     */
    private int code;

    /**
     * The actual payload (may be null if no data).
     */
    private T data;
}
