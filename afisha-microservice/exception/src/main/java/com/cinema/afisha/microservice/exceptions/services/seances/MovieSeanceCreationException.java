package com.cinema.afisha.microservice.exceptions.services.seances;

import com.cinema.afisha.microservice.exceptions.services.EntityCreationException;

public class MovieSeanceCreationException extends EntityCreationException {
    public MovieSeanceCreationException() {
    }

    public MovieSeanceCreationException(String message) {
        super(message);
    }

    public MovieSeanceCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieSeanceCreationException(Throwable cause) {
        super(cause);
    }

    public MovieSeanceCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
