package com.cinema.cinema.project.cinemas.microservice.exceptions.services.sits.types;

import com.cinema.cinema.project.cinemas.microservice.exceptions.services.EntityAlreadyExistException;

public class SitTypeAlreadyExistException extends EntityAlreadyExistException {
    public SitTypeAlreadyExistException() {
    }

    public SitTypeAlreadyExistException(String message) {
        super(message);
    }

    public SitTypeAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public SitTypeAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public SitTypeAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
