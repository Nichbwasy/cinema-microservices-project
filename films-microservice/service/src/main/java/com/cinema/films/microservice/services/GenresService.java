package com.cinema.films.microservice.services;

import com.cinema.films.microservice.domains.GenreDto;
import com.cinema.films.microservice.exceptions.services.genres.*;
import com.cinema.films.microservice.models.Genre;

import java.util.List;

public interface GenresService {
    List<GenreDto> getAllGenres();
    GenreDto getGenre(Long id) throws GenreNotFoundException;
    GenreDto createGenre(Genre genre) throws GenreAlreadyExistException, GenreCreationException;
    GenreDto updateGenre(Genre genre) throws GenreNotFoundException, GenreUpdatingException;
    Long deleteGenre(Long id) throws GenreNotFoundException, GenreDeletingException;
}
