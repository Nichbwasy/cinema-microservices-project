package com.cinema.films.microservice.exceptions.services.films.img.resources;

import com.cinema.films.microservice.exceptions.services.EntityCreationException;

public class FilmImgResourceCreationException extends EntityCreationException {
    public FilmImgResourceCreationException() {
    }

    public FilmImgResourceCreationException(String message) {
        super(message);
    }

    public FilmImgResourceCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilmImgResourceCreationException(Throwable cause) {
        super(cause);
    }

    public FilmImgResourceCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
