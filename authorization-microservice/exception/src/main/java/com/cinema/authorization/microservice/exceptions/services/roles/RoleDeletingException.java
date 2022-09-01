package com.cinema.authorization.microservice.exceptions.services.roles;

import com.cinema.authorization.microservice.exceptions.services.EntityDeletingException;

public class RoleDeletingException extends EntityDeletingException {
    public RoleDeletingException() {
    }

    public RoleDeletingException(String message) {
        super(message);
    }

    public RoleDeletingException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleDeletingException(Throwable cause) {
        super(cause);
    }

    public RoleDeletingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
