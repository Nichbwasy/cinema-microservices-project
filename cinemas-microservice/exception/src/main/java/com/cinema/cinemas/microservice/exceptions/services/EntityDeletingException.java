package com.cinema.cinemas.microservice.exceptions.services;

public class EntityDeletingException extends RuntimeException {
    public EntityDeletingException() {
    }

    public EntityDeletingException(String message) {
        super(message);
    }

    public EntityDeletingException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityDeletingException(Throwable cause) {
        super(cause);
    }

    public EntityDeletingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
