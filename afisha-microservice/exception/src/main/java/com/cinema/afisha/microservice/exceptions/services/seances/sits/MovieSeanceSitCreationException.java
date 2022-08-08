package com.cinema.afisha.microservice.exceptions.services.seances.sits;

import com.cinema.afisha.microservice.exceptions.services.EntityCreationException;

public class MovieSeanceSitCreationException extends EntityCreationException {
    public MovieSeanceSitCreationException() {
    }

    public MovieSeanceSitCreationException(String message) {
        super(message);
    }

    public MovieSeanceSitCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieSeanceSitCreationException(Throwable cause) {
        super(cause);
    }

    public MovieSeanceSitCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
