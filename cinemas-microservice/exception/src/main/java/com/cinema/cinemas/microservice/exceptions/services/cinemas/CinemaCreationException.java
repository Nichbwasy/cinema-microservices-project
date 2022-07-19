package com.cinema.cinemas.microservice.exceptions.services.cinemas;

import com.cinema.cinemas.microservice.exceptions.services.EntityCreationException;

public class CinemaCreationException extends EntityCreationException {
    public CinemaCreationException() {
    }

    public CinemaCreationException(String message) {
        super(message);
    }

    public CinemaCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CinemaCreationException(Throwable cause) {
        super(cause);
    }

    public CinemaCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
