package com.cinema.films.microservice.exceptions.services.genres;

import com.cinema.films.microservice.exceptions.services.EntityAlreadyExistException;

public class GenreAlreadyExistException extends EntityAlreadyExistException {
    public GenreAlreadyExistException() {
    }

    public GenreAlreadyExistException(String message) {
        super(message);
    }

    public GenreAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenreAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public GenreAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
