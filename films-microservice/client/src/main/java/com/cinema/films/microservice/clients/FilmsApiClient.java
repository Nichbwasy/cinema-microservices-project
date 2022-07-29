package com.cinema.films.microservice.clients;

import com.cinema.films.microservice.domains.FilmDto;

public interface FilmsApiClient {
    FilmDto getFilm(Long id);
}
