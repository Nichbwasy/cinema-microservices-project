package com.cinema.common.utils.authorizations.jwt.exceptions;

public class ClientJwtException extends RuntimeException {
    public ClientJwtException() {
    }

    public ClientJwtException(String message) {
        super(message);
    }

    public ClientJwtException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientJwtException(Throwable cause) {
        super(cause);
    }

    public ClientJwtException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
