package com.cinema.films.microservice.exceptions.services.directors;

import com.cinema.films.microservice.exceptions.services.EntityAlreadyExistException;

public class DirectorAlreadyExistException extends EntityAlreadyExistException {
    public DirectorAlreadyExistException() {
    }

    public DirectorAlreadyExistException(String message) {
        super(message);
    }

    public DirectorAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public DirectorAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public DirectorAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
