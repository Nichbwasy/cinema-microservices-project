package com.cinema.films.microservice.exceptions.services.films;

import com.cinema.films.microservice.exceptions.services.EntityDeletingException;

public class FilmDeletingException extends EntityDeletingException {
    public FilmDeletingException() {
    }

    public FilmDeletingException(String message) {
        super(message);
    }

    public FilmDeletingException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilmDeletingException(Throwable cause) {
        super(cause);
    }

    public FilmDeletingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
