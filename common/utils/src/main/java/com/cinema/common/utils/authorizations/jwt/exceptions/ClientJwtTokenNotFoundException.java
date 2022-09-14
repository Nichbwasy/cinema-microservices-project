package com.cinema.common.utils.authorizations.jwt.exceptions;

public class ClientJwtTokenNotFoundException extends ClientJwtException {
    public ClientJwtTokenNotFoundException() {
    }

    public ClientJwtTokenNotFoundException(String message) {
        super(message);
    }

    public ClientJwtTokenNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientJwtTokenNotFoundException(Throwable cause) {
        super(cause);
    }

    public ClientJwtTokenNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
