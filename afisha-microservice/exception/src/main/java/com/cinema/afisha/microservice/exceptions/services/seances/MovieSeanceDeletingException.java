package com.cinema.afisha.microservice.exceptions.services.seances;

import com.cinema.afisha.microservice.exceptions.services.EntityDeletingException;

public class MovieSeanceDeletingException extends EntityDeletingException {
    public MovieSeanceDeletingException() {
    }

    public MovieSeanceDeletingException(String message) {
        super(message);
    }

    public MovieSeanceDeletingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieSeanceDeletingException(Throwable cause) {
        super(cause);
    }

    public MovieSeanceDeletingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
