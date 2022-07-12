package com.cinema.cinema.project.cinemas.microservice.exceptions.services.halls;

import com.cinema.cinema.project.cinemas.microservice.exceptions.services.EntityUpdatingException;

public class HallUpdatingException extends EntityUpdatingException {
    public HallUpdatingException() {
    }

    public HallUpdatingException(String message) {
        super(message);
    }

    public HallUpdatingException(String message, Throwable cause) {
        super(message, cause);
    }

    public HallUpdatingException(Throwable cause) {
        super(cause);
    }

    public HallUpdatingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
