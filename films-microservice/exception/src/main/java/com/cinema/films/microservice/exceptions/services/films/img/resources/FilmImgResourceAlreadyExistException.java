package com.cinema.films.microservice.exceptions.services.films.img.resources;

import com.cinema.films.microservice.exceptions.services.EntityAlreadyExistException;

public class FilmImgResourceAlreadyExistException extends EntityAlreadyExistException {
    public FilmImgResourceAlreadyExistException() {
    }

    public FilmImgResourceAlreadyExistException(String message) {
        super(message);
    }

    public FilmImgResourceAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilmImgResourceAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public FilmImgResourceAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
