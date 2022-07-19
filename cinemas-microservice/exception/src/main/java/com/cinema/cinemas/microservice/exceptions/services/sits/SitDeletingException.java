package com.cinema.cinemas.microservice.exceptions.services.sits;

import com.cinema.cinemas.microservice.exceptions.services.EntityDeletingException;

public class SitDeletingException extends EntityDeletingException {
    public SitDeletingException() {
    }

    public SitDeletingException(String message) {
        super(message);
    }

    public SitDeletingException(String message, Throwable cause) {
        super(message, cause);
    }

    public SitDeletingException(Throwable cause) {
        super(cause);
    }

    public SitDeletingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
