package com.cinema.afisha.microservice.exceptions.services.seances.sits;

import com.cinema.afisha.microservice.exceptions.services.EntityNotFoundException;

public class MovieSeanceSitNotFoundException extends EntityNotFoundException {
    public MovieSeanceSitNotFoundException() {
    }

    public MovieSeanceSitNotFoundException(String message) {
        super(message);
    }

    public MovieSeanceSitNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieSeanceSitNotFoundException(Throwable cause) {
        super(cause);
    }

    public MovieSeanceSitNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
