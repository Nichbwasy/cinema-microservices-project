package com.cinema.cinema.project.cinemas.microservice.exceptions.services.sits.types;

import com.cinema.cinema.project.cinemas.microservice.exceptions.services.EntityUpdatingException;

public class SitTypeUpdatingException extends EntityUpdatingException {
    public SitTypeUpdatingException() {
    }

    public SitTypeUpdatingException(String message) {
        super(message);
    }

    public SitTypeUpdatingException(String message, Throwable cause) {
        super(message, cause);
    }

    public SitTypeUpdatingException(Throwable cause) {
        super(cause);
    }

    public SitTypeUpdatingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
