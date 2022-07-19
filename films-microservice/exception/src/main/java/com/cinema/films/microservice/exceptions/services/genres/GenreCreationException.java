package com.cinema.films.microservice.exceptions.services.genres;

import com.cinema.films.microservice.exceptions.services.EntityCreationException;

public class GenreCreationException extends EntityCreationException {
    public GenreCreationException() {
    }

    public GenreCreationException(String message) {
        super(message);
    }

    public GenreCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenreCreationException(Throwable cause) {
        super(cause);
    }

    public GenreCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
