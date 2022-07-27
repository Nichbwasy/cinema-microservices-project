package com.cinema.films.microservice.exceptions.dao.minio;

public class MinIoException extends RuntimeException {
    public MinIoException() {
        super();
    }

    public MinIoException(String message) {
        super(message);
    }

    public MinIoException(String message, Throwable cause) {
        super(message, cause);
    }

    public MinIoException(Throwable cause) {
        super(cause);
    }

    protected MinIoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
