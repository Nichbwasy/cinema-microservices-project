package com.cinema.cinemas.microservice.exceptions.services.cinemas;

import com.cinema.cinemas.microservice.exceptions.services.EntityAlreadyExistException;

public class CinemaAlreadyExistException extends EntityAlreadyExistException {
    public CinemaAlreadyExistException() {
    }

    public CinemaAlreadyExistException(String message) {
        super(message);
    }

    public CinemaAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public CinemaAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public CinemaAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
