package com.cinema.films.microservice.exceptions.services.directors;

import com.cinema.films.microservice.exceptions.services.EntityDeletingException;

public class DirectorDeletingException extends EntityDeletingException {
    public DirectorDeletingException() {
    }

    public DirectorDeletingException(String message) {
        super(message);
    }

    public DirectorDeletingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DirectorDeletingException(Throwable cause) {
        super(cause);
    }

    public DirectorDeletingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
