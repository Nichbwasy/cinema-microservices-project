package com.cinema.films.microservice.exceptions.services.genres;

import com.cinema.films.microservice.exceptions.services.EntityNotFoundException;

public class GenreNotFoundException extends EntityNotFoundException {
    public GenreNotFoundException() {
    }

    public GenreNotFoundException(String message) {
        super(message);
    }

    public GenreNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenreNotFoundException(Throwable cause) {
        super(cause);
    }

    public GenreNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
