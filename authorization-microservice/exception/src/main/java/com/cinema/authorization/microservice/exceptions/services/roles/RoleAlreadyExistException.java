package com.cinema.authorization.microservice.exceptions.services.roles;

import com.cinema.authorization.microservice.exceptions.services.EntityAlreadyExistException;

public class RoleAlreadyExistException extends EntityAlreadyExistException {
    public RoleAlreadyExistException() {
    }

    public RoleAlreadyExistException(String message) {
        super(message);
    }

    public RoleAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public RoleAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
