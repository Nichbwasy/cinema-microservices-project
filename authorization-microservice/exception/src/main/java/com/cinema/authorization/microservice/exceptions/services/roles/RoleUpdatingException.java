package com.cinema.authorization.microservice.exceptions.services.roles;

import com.cinema.authorization.microservice.exceptions.services.EntityUpdatingException;

public class RoleUpdatingException extends EntityUpdatingException {
    public RoleUpdatingException() {
    }

    public RoleUpdatingException(String message) {
        super(message);
    }

    public RoleUpdatingException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleUpdatingException(Throwable cause) {
        super(cause);
    }

    public RoleUpdatingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
