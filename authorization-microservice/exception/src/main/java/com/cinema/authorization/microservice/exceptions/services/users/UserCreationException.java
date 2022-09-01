package com.cinema.authorization.microservice.exceptions.services.users;

import com.cinema.authorization.microservice.exceptions.services.EntityCreationException;

public class UserCreationException extends EntityCreationException {
    public UserCreationException() {
    }

    public UserCreationException(String message) {
        super(message);
    }

    public UserCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserCreationException(Throwable cause) {
        super(cause);
    }

    public UserCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
