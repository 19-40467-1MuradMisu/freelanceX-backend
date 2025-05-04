package com.freelancex.userservice.exception;

public class JwtException extends Exception {
    public JwtException() {
    }

    public JwtException(String message) {
        super(message);
    }
}
