package com.cinema.films.microservice.exceptions.services.directors;

import com.cinema.films.microservice.exceptions.services.EntityUpdatingException;

public class DirectorUpdatingException extends EntityUpdatingException {
    public DirectorUpdatingException() {
    }

    public DirectorUpdatingException(String message) {
        super(message);
    }

    public DirectorUpdatingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DirectorUpdatingException(Throwable cause) {
        super(cause);
    }

    public DirectorUpdatingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
