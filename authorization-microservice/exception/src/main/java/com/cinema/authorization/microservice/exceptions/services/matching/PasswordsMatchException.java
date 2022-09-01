package com.cinema.authorization.microservice.exceptions.services.matching;

public class PasswordsMatchException extends RuntimeException {
    public PasswordsMatchException() {
    }

    public PasswordsMatchException(String message) {
        super(message);
    }

    public PasswordsMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordsMatchException(Throwable cause) {
        super(cause);
    }

    public PasswordsMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
