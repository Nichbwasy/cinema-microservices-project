package com.cinema.cinemas.microservice.exceptions.clients;

public class CinemasMicroserviceApiException extends RuntimeException {
    public CinemasMicroserviceApiException() {
    }

    public CinemasMicroserviceApiException(String message) {
        super(message);
    }

    public CinemasMicroserviceApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public CinemasMicroserviceApiException(Throwable cause) {
        super(cause);
    }

    public CinemasMicroserviceApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
