package com.cinema.films.microservice.exceptions.services.genres;

import com.cinema.films.microservice.exceptions.services.EntityDeletingException;

public class GenreDeletingException extends EntityDeletingException {
    public GenreDeletingException() {
    }

    public GenreDeletingException(String message) {
        super(message);
    }

    public GenreDeletingException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenreDeletingException(Throwable cause) {
        super(cause);
    }

    public GenreDeletingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
