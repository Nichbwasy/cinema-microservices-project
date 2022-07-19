package com.cinema.films.microservice.exceptions.services.films;

import com.cinema.films.microservice.exceptions.services.EntityUpdatingException;

public class FilmUpdatingException extends EntityUpdatingException {
    public FilmUpdatingException() {
    }

    public FilmUpdatingException(String message) {
        super(message);
    }

    public FilmUpdatingException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilmUpdatingException(Throwable cause) {
        super(cause);
    }

    public FilmUpdatingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
