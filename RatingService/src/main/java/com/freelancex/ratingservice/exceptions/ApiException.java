package com.freelancex.ratingservice.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {

    @Getter
    private final HttpStatus statusCode;

    public ApiException() {
        super();
        this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public ApiException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
      public HttpStatus getStatus() {
        return statusCode;
    }
}
