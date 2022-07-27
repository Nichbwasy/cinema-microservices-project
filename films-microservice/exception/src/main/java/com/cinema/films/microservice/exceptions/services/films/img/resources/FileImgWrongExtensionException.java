package com.cinema.films.microservice.exceptions.services.films.img.resources;

public class FileImgWrongExtensionException extends RuntimeException{
    public FileImgWrongExtensionException() {
    }

    public FileImgWrongExtensionException(String message) {
        super(message);
    }

    public FileImgWrongExtensionException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileImgWrongExtensionException(Throwable cause) {
        super(cause);
    }

    public FileImgWrongExtensionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
