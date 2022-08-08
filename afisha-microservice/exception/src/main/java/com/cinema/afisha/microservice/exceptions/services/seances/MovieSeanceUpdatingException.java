package com.cinema.afisha.microservice.exceptions.services.seances;

import com.cinema.afisha.microservice.exceptions.services.EntityUpdatingException;

public class MovieSeanceUpdatingException extends EntityUpdatingException {
    public MovieSeanceUpdatingException() {
    }

    public MovieSeanceUpdatingException(String message) {
        super(message);
    }

    public MovieSeanceUpdatingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieSeanceUpdatingException(Throwable cause) {
        super(cause);
    }

    public MovieSeanceUpdatingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
