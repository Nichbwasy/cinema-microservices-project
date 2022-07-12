package com.cinema.cinema.project.cinemas.microservice.exceptions.services.halls;

import com.cinema.cinema.project.cinemas.microservice.exceptions.services.EntityDeletingException;

public class HallDeletingException extends EntityDeletingException {
    public HallDeletingException() {
    }

    public HallDeletingException(String message) {
        super(message);
    }

    public HallDeletingException(String message, Throwable cause) {
        super(message, cause);
    }

    public HallDeletingException(Throwable cause) {
        super(cause);
    }

    public HallDeletingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
