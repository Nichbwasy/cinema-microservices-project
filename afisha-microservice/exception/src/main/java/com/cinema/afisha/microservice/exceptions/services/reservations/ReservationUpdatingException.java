package com.cinema.afisha.microservice.exceptions.services.reservations;

import com.cinema.afisha.microservice.exceptions.services.EntityUpdatingException;

public class ReservationUpdatingException extends EntityUpdatingException {
    public ReservationUpdatingException() {
    }

    public ReservationUpdatingException(String message) {
        super(message);
    }

    public ReservationUpdatingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReservationUpdatingException(Throwable cause) {
        super(cause);
    }

    public ReservationUpdatingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
