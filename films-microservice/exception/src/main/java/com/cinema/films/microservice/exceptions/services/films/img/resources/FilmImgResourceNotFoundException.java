package com.cinema.films.microservice.exceptions.services.films.img.resources;

import com.cinema.films.microservice.exceptions.services.EntityNotFoundException;

public class FilmImgResourceNotFoundException extends EntityNotFoundException {
    public FilmImgResourceNotFoundException() {
    }

    public FilmImgResourceNotFoundException(String message) {
        super(message);
    }

    public FilmImgResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilmImgResourceNotFoundException(Throwable cause) {
        super(cause);
    }

    public FilmImgResourceNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
