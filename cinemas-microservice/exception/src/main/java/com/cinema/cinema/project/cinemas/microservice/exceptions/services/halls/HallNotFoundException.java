package com.cinema.cinema.project.cinemas.microservice.exceptions.services.halls;

import com.cinema.cinema.project.cinemas.microservice.exceptions.services.EntityNotFoundException;

public class HallNotFoundException extends EntityNotFoundException {
    public HallNotFoundException() {
    }

    public HallNotFoundException(String message) {
        super(message);
    }

    public HallNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public HallNotFoundException(Throwable cause) {
        super(cause);
    }

    public HallNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
