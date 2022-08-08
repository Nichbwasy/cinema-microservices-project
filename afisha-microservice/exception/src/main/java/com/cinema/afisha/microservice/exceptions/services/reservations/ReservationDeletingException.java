package com.cinema.afisha.microservice.exceptions.services.reservations;

import com.cinema.afisha.microservice.exceptions.services.EntityDeletingException;

public class ReservationDeletingException extends EntityDeletingException {
    public ReservationDeletingException() {
    }

    public ReservationDeletingException(String message) {
        super(message);
    }

    public ReservationDeletingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReservationDeletingException(Throwable cause) {
        super(cause);
    }

    public ReservationDeletingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
