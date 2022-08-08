package com.cinema.afisha.microservice.exceptions.services.seances;

import com.cinema.afisha.microservice.exceptions.services.EntityAlreadyExistException;

public class MovieSeanceAlreadyExistException extends EntityAlreadyExistException {
    public MovieSeanceAlreadyExistException() {
        super();
    }

    public MovieSeanceAlreadyExistException(String message) {
        super(message);
    }

    public MovieSeanceAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieSeanceAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public MovieSeanceAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
