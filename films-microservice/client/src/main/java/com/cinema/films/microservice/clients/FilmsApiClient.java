package com.cinema.films.microservice.clients;

import com.cinema.films.microservice.domains.FilmDto;
import com.cinema.films.microservice.exceptions.clients.FilmsMicroserviceApiException;

import java.io.InputStream;

public interface FilmsApiClient {
    /**
     * Returns film by if from FILMS-MICROSERVICE.
     * @param id Id of film.
     * @return DTO of film.
     * @throws FilmsMicroserviceApiException Throws when exception was occurred in API client.
     */
    FilmDto getFilm(Long id) throws FilmsMicroserviceApiException;

    /**
     * Returns img poster for a film by film id from FILMS-MICROSERVICE.
     * @param filmId Id of film.
     * @return DTO of film.
     * @throws FilmsMicroserviceApiException Throws when exception was occurred in API client.
     */
    InputStream getFilmPoster(Long filmId) throws FilmsMicroserviceApiException;
}
