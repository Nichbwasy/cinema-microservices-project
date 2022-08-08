package com.cinema.afisha.microservice.exceptions.services.reservations;

import com.cinema.afisha.microservice.exceptions.services.EntityNotFoundException;

public class ReservationNotFoundException extends EntityNotFoundException {
    public ReservationNotFoundException() {
    }

    public ReservationNotFoundException(String message) {
        super(message);
    }

    public ReservationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReservationNotFoundException(Throwable cause) {
        super(cause);
    }

    public ReservationNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
