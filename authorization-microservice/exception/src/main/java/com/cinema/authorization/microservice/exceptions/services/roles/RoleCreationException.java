package com.cinema.authorization.microservice.exceptions.services.roles;

import com.cinema.authorization.microservice.exceptions.services.EntityCreationException;

public class RoleCreationException extends EntityCreationException {
    public RoleCreationException() {
    }

    public RoleCreationException(String message) {
        super(message);
    }

    public RoleCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleCreationException(Throwable cause) {
        super(cause);
    }

    public RoleCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
