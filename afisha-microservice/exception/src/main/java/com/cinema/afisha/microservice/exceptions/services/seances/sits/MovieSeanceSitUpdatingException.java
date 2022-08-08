package com.cinema.afisha.microservice.exceptions.services.seances.sits;

import com.cinema.afisha.microservice.exceptions.services.EntityUpdatingException;

public class MovieSeanceSitUpdatingException extends EntityUpdatingException {
    public MovieSeanceSitUpdatingException() {
    }

    public MovieSeanceSitUpdatingException(String message) {
        super(message);
    }

    public MovieSeanceSitUpdatingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieSeanceSitUpdatingException(Throwable cause) {
        super(cause);
    }

    public MovieSeanceSitUpdatingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
