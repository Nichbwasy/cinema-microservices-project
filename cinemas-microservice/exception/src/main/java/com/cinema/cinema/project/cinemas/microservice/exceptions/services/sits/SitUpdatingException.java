package com.cinema.cinema.project.cinemas.microservice.exceptions.services.sits;

import com.cinema.cinema.project.cinemas.microservice.exceptions.services.EntityUpdatingException;

public class SitUpdatingException extends EntityUpdatingException {
    public SitUpdatingException() {
    }

    public SitUpdatingException(String message) {
        super(message);
    }

    public SitUpdatingException(String message, Throwable cause) {
        super(message, cause);
    }

    public SitUpdatingException(Throwable cause) {
        super(cause);
    }

    public SitUpdatingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
