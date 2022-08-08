package com.cinema.afisha.microservice.exceptions.services.seances.api;

import com.cinema.afisha.microservice.exceptions.services.ServicesClientApiException;

public class MovieSeanceApiException extends ServicesClientApiException {

    public MovieSeanceApiException() {
    }

    public MovieSeanceApiException(String message) {
        super(message);
    }

    public MovieSeanceApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieSeanceApiException(Throwable cause) {
        super(cause);
    }

    public MovieSeanceApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
