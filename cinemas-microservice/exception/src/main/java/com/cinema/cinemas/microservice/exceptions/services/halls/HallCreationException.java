package com.cinema.cinemas.microservice.exceptions.services.halls;

import com.cinema.cinemas.microservice.exceptions.services.EntityCreationException;

public class HallCreationException extends EntityCreationException {
    public HallCreationException() {
    }

    public HallCreationException(String message) {
        super(message);
    }

    public HallCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public HallCreationException(Throwable cause) {
        super(cause);
    }

    public HallCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
