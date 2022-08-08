package com.cinema.cinemas.microservice.exceptions.services.halls;

import com.cinema.cinemas.microservice.exceptions.services.EntityNotFoundException;

public class HallNotBelongCinemaException extends EntityNotFoundException {
    public HallNotBelongCinemaException() {
    }

    public HallNotBelongCinemaException(String message) {
        super(message);
    }

    public HallNotBelongCinemaException(String message, Throwable cause) {
        super(message, cause);
    }

    public HallNotBelongCinemaException(Throwable cause) {
        super(cause);
    }

    public HallNotBelongCinemaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
