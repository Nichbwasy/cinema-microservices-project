package com.cinema.afisha.microservice.exceptions.services.reservations;

import com.cinema.afisha.microservice.exceptions.services.EntityCreationException;

public class ReservationCreationException extends EntityCreationException {
    public ReservationCreationException() {
    }

    public ReservationCreationException(String message) {
        super(message);
    }

    public ReservationCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReservationCreationException(Throwable cause) {
        super(cause);
    }

    public ReservationCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
