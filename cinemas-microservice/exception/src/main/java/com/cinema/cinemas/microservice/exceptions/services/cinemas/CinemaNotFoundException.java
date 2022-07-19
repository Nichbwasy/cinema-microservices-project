package com.cinema.cinemas.microservice.exceptions.services.cinemas;

import com.cinema.cinemas.microservice.exceptions.services.EntityNotFoundException;

public class CinemaNotFoundException extends EntityNotFoundException {
    public CinemaNotFoundException() {
    }

    public CinemaNotFoundException(String message) {
        super(message);
    }

    public CinemaNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CinemaNotFoundException(Throwable cause) {
        super(cause);
    }

    public CinemaNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
