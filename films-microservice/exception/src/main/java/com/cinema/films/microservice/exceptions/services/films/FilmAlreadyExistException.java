package com.cinema.films.microservice.exceptions.services.films;

import com.cinema.films.microservice.exceptions.services.EntityAlreadyExistException;

public class FilmAlreadyExistException extends EntityAlreadyExistException {
    public FilmAlreadyExistException() {
    }

    public FilmAlreadyExistException(String message) {
        super(message);
    }

    public FilmAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilmAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public FilmAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
