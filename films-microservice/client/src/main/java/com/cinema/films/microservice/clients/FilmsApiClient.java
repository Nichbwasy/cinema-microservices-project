package com.cinema.films.microservice.clients;

import com.cinema.films.microservice.domains.FilmDto;

import java.io.InputStream;

public interface FilmsApiClient {
    FilmDto getFilm(Long id);
    InputStream getFilmPoster(Long filmId);
}
