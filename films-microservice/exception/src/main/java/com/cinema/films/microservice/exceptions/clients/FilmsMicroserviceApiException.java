package com.cinema.films.microservice.exceptions.clients;

public class FilmsMicroserviceApiException extends RuntimeException {
    public FilmsMicroserviceApiException() {
    }

    public FilmsMicroserviceApiException(String message) {
        super(message);
    }

    public FilmsMicroserviceApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilmsMicroserviceApiException(Throwable cause) {
        super(cause);
    }

    public FilmsMicroserviceApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
