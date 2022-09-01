package com.cinema.authorization.microservice.exceptions.services.users;

import com.cinema.authorization.microservice.exceptions.services.EntityDeletingException;

public class UserDeletingException extends EntityDeletingException {
    public UserDeletingException() {
    }

    public UserDeletingException(String message) {
        super(message);
    }

    public UserDeletingException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserDeletingException(Throwable cause) {
        super(cause);
    }

    public UserDeletingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
