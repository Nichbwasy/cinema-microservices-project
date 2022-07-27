package com.cinema.films.microservice.exceptions.services.films.img;

public class FilmImgExtensionException extends RuntimeException {
    public FilmImgExtensionException() {
    }

    public FilmImgExtensionException(String message) {
        super(message);
    }

    public FilmImgExtensionException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilmImgExtensionException(Throwable cause) {
        super(cause);
    }

    public FilmImgExtensionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
