package com.cinema.cinema.project.cinemas.microservice.exceptions.services.sits.types;

import com.cinema.cinema.project.cinemas.microservice.exceptions.services.EntityDeletingException;

public class SitTypeDeletingException extends EntityDeletingException {
    public SitTypeDeletingException() {
    }

    public SitTypeDeletingException(String message) {
        super(message);
    }

    public SitTypeDeletingException(String message, Throwable cause) {
        super(message, cause);
    }

    public SitTypeDeletingException(Throwable cause) {
        super(cause);
    }

    public SitTypeDeletingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
