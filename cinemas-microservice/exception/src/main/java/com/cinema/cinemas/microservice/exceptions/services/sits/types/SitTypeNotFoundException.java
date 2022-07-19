package com.cinema.cinemas.microservice.exceptions.services.sits.types;

import com.cinema.cinemas.microservice.exceptions.services.EntityNotFoundException;

public class SitTypeNotFoundException extends EntityNotFoundException {
    public SitTypeNotFoundException() {
    }

    public SitTypeNotFoundException(String message) {
        super(message);
    }

    public SitTypeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SitTypeNotFoundException(Throwable cause) {
        super(cause);
    }

    public SitTypeNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
