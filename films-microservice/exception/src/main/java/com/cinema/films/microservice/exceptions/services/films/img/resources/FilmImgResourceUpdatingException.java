package com.cinema.films.microservice.exceptions.services.films.img.resources;

import com.cinema.films.microservice.exceptions.services.EntityUpdatingException;

public class FilmImgResourceUpdatingException extends EntityUpdatingException {
    public FilmImgResourceUpdatingException() {
    }

    public FilmImgResourceUpdatingException(String message) {
        super(message);
    }

    public FilmImgResourceUpdatingException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilmImgResourceUpdatingException(Throwable cause) {
        super(cause);
    }

    public FilmImgResourceUpdatingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
