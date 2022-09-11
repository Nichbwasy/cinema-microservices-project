package com.cinema.common.utils.authorizations.jwt.exceptions;

public class ClientJwtTokenValidationException extends ClientJwtException {
    public ClientJwtTokenValidationException() {
    }

    public ClientJwtTokenValidationException(String message) {
        super(message);
    }

    public ClientJwtTokenValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientJwtTokenValidationException(Throwable cause) {
        super(cause);
    }

    public ClientJwtTokenValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
