package com.cinema.afisha.microservice.exceptions.services.seances;

import com.cinema.afisha.microservice.exceptions.services.EntityNotFoundException;

public class MovieSeanceNotFoundException extends EntityNotFoundException {
    public MovieSeanceNotFoundException() {
    }

    public MovieSeanceNotFoundException(String message) {
        super(message);
    }

    public MovieSeanceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieSeanceNotFoundException(Throwable cause) {
        super(cause);
    }

    public MovieSeanceNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
