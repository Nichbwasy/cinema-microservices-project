package com.cinema.films.microservice.exceptions.services.films;

import com.cinema.films.microservice.exceptions.services.EntityCreationException;

public class FilmCreationException extends EntityCreationException {
    public FilmCreationException() {
    }

    public FilmCreationException(String message) {
        super(message);
    }

    public FilmCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilmCreationException(Throwable cause) {
        super(cause);
    }

    public FilmCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
