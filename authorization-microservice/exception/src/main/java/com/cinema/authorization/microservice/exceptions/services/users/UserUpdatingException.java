package com.cinema.authorization.microservice.exceptions.services.users;

import com.cinema.authorization.microservice.exceptions.services.EntityUpdatingException;

public class UserUpdatingException extends EntityUpdatingException {
    public UserUpdatingException() {
    }

    public UserUpdatingException(String message) {
        super(message);
    }

    public UserUpdatingException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserUpdatingException(Throwable cause) {
        super(cause);
    }

    public UserUpdatingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
