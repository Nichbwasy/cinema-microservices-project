package com.cinema.afisha.microservice.exceptions.services;

public class ServicesClientApiException extends RuntimeException {
    public ServicesClientApiException() {
    }

    public ServicesClientApiException(String message) {
        super(message);
    }

    public ServicesClientApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServicesClientApiException(Throwable cause) {
        super(cause);
    }

    public ServicesClientApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
