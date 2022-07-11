package com.cinema.cinema.project.cinemas.microservice.exceptions.services.sits.types;

import com.cinema.cinema.project.cinemas.microservice.exceptions.services.EntityCreationException;

public class SitTypeCreationException extends EntityCreationException {

    public SitTypeCreationException() {
    }

    public SitTypeCreationException(String message) {
        super(message);
    }

    public SitTypeCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SitTypeCreationException(Throwable cause) {
        super(cause);
    }

    public SitTypeCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
