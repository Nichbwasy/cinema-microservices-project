package com.cinema.afisha.microservice.exceptions.services.seances.sits;

import com.cinema.afisha.microservice.exceptions.services.EntityAlreadyExistException;

public class MovieSeanceSitAlreadyExistException extends EntityAlreadyExistException {
    public MovieSeanceSitAlreadyExistException() {
    }

    public MovieSeanceSitAlreadyExistException(String message) {
        super(message);
    }

    public MovieSeanceSitAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieSeanceSitAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public MovieSeanceSitAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
