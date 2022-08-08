package com.cinema.afisha.microservice.exceptions.services.reservations;

import com.cinema.afisha.microservice.exceptions.services.EntityAlreadyExistException;

public class ReservationAlreadyExistException extends EntityAlreadyExistException {
    public ReservationAlreadyExistException() {
    }

    public ReservationAlreadyExistException(String message) {
        super(message);
    }

    public ReservationAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReservationAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public ReservationAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
