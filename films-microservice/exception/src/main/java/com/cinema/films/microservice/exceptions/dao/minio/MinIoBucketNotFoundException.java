package com.cinema.films.microservice.exceptions.dao.minio;

public class MinIoBucketNotFoundException extends MinIoException {
    public MinIoBucketNotFoundException() {
        super();
    }

    public MinIoBucketNotFoundException(String message) {
        super(message);
    }

    public MinIoBucketNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MinIoBucketNotFoundException(Throwable cause) {
        super(cause);
    }

    protected MinIoBucketNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
