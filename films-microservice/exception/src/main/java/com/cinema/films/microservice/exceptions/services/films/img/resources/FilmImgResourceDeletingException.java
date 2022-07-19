package com.cinema.films.microservice.exceptions.services.films.img.resources;

import com.cinema.films.microservice.exceptions.services.EntityDeletingException;

public class FilmImgResourceDeletingException extends EntityDeletingException {
    public FilmImgResourceDeletingException() {
    }

    public FilmImgResourceDeletingException(String message) {
        super(message);
    }

    public FilmImgResourceDeletingException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilmImgResourceDeletingException(Throwable cause) {
        super(cause);
    }

    public FilmImgResourceDeletingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
