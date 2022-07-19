package com.cinema.films.microservice.exceptions.services.directors;

import com.cinema.films.microservice.exceptions.services.EntityCreationException;

public class DirectorCreationException extends EntityCreationException {
    public DirectorCreationException() {
    }

    public DirectorCreationException(String message) {
        super(message);
    }

    public DirectorCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DirectorCreationException(Throwable cause) {
        super(cause);
    }

    public DirectorCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
