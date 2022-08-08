package com.cinema.afisha.microservice.exceptions.services;

public class EntityUpdatingException extends RuntimeException {
    public EntityUpdatingException() {
    }

    public EntityUpdatingException(String message) {
        super(message);
    }

    public EntityUpdatingException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityUpdatingException(Throwable cause) {
        super(cause);
    }

    public EntityUpdatingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
