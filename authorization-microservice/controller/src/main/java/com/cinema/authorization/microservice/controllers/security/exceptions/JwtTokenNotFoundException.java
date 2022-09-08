package com.cinema.authorization.microservice.controllers.security.exceptions;

public class JwtTokenNotFoundException extends JwtException {
    public JwtTokenNotFoundException() {
    }

    public JwtTokenNotFoundException(String message) {
        super(message);
    }

    public JwtTokenNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtTokenNotFoundException(Throwable cause) {
        super(cause);
    }

    public JwtTokenNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
