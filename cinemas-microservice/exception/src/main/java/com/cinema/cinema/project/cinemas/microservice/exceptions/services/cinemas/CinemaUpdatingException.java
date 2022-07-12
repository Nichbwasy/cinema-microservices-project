package com.cinema.cinema.project.cinemas.microservice.exceptions.services.cinemas;

import com.cinema.cinema.project.cinemas.microservice.exceptions.services.EntityUpdatingException;

public class CinemaUpdatingException extends EntityUpdatingException {
    public CinemaUpdatingException() {
    }

    public CinemaUpdatingException(String message) {
        super(message);
    }

    public CinemaUpdatingException(String message, Throwable cause) {
        super(message, cause);
    }

    public CinemaUpdatingException(Throwable cause) {
        super(cause);
    }

    public CinemaUpdatingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
