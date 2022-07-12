package com.cinema.cinema.project.cinemas.microservice.exceptions.services.cinemas;

import com.cinema.cinema.project.cinemas.microservice.exceptions.services.EntityDeletingException;

public class CinemaDeletingException extends EntityDeletingException {
    public CinemaDeletingException() {
    }

    public CinemaDeletingException(String message) {
        super(message);
    }

    public CinemaDeletingException(String message, Throwable cause) {
        super(message, cause);
    }

    public CinemaDeletingException(Throwable cause) {
        super(cause);
    }

    public CinemaDeletingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
