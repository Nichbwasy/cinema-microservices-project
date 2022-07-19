package com.cinema.cinemas.microservice.exceptions.services.sits;

import com.cinema.cinemas.microservice.exceptions.services.EntityAlreadyExistException;

public class SitAlreadyExistException extends EntityAlreadyExistException {

    public SitAlreadyExistException() {
    }

    public SitAlreadyExistException(String message) {
        super(message);
    }

    public SitAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public SitAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public SitAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
