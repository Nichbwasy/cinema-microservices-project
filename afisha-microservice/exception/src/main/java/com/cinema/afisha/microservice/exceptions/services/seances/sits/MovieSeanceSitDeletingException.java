package com.cinema.afisha.microservice.exceptions.services.seances.sits;

import com.cinema.afisha.microservice.exceptions.services.EntityDeletingException;

public class MovieSeanceSitDeletingException extends EntityDeletingException {
    public MovieSeanceSitDeletingException() {
    }

    public MovieSeanceSitDeletingException(String message) {
        super(message);
    }

    public MovieSeanceSitDeletingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieSeanceSitDeletingException(Throwable cause) {
        super(cause);
    }

    public MovieSeanceSitDeletingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
