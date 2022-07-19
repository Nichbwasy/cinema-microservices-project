package com.cinema.films.microservice.exceptions.services.genres;

import com.cinema.films.microservice.exceptions.services.EntityUpdatingException;

public class GenreUpdatingException extends EntityUpdatingException {
    public GenreUpdatingException() {
    }

    public GenreUpdatingException(String message) {
        super(message);
    }

    public GenreUpdatingException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenreUpdatingException(Throwable cause) {
        super(cause);
    }

    public GenreUpdatingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
