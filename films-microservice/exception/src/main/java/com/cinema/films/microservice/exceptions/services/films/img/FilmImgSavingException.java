package com.cinema.films.microservice.exceptions.services.films.img;

public class FilmImgSavingException extends RuntimeException {
    public FilmImgSavingException() {
    }

    public FilmImgSavingException(String message) {
        super(message);
    }

    public FilmImgSavingException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilmImgSavingException(Throwable cause) {
        super(cause);
    }

    public FilmImgSavingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
