package com.cinema.authorization.microservice.controllers.security.exceptions;

public class JwtTokenValidationException extends JwtException {
    public JwtTokenValidationException() {
    }

    public JwtTokenValidationException(String message) {
        super(message);
    }

    public JwtTokenValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtTokenValidationException(Throwable cause) {
        super(cause);
    }

    public JwtTokenValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
