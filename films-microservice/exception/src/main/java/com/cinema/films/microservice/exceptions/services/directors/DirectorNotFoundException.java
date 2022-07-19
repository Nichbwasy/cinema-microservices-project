package com.cinema.films.microservice.exceptions.services.directors;

import com.cinema.films.microservice.exceptions.services.EntityNotFoundException;

public class DirectorNotFoundException extends EntityNotFoundException {
    public DirectorNotFoundException() {
    }

    public DirectorNotFoundException(String message) {
        super(message);
    }

    public DirectorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DirectorNotFoundException(Throwable cause) {
        super(cause);
    }

    public DirectorNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
