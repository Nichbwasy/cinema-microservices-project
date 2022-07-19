package com.cinema.cinemas.microservice.exceptions.services.sits;

import com.cinema.cinemas.microservice.exceptions.services.EntityNotFoundException;

public class SitNotFoundException extends EntityNotFoundException {
    public SitNotFoundException() {
    }

    public SitNotFoundException(String message) {
        super(message);
    }

    public SitNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SitNotFoundException(Throwable cause) {
        super(cause);
    }

    public SitNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
