package com.cinema.cinemas.microservice.exceptions.services.sits;

import com.cinema.cinemas.microservice.exceptions.services.EntityCreationException;

public class SitCreationException extends EntityCreationException {
    public SitCreationException() {
        super();
    }

    public SitCreationException(String message) {
        super(message);
    }

    public SitCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SitCreationException(Throwable cause) {
        super(cause);
    }

    public SitCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
