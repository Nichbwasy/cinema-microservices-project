package com.cinema.films.microservice.services;

import com.cinema.films.microservice.domains.DirectorDto;
import com.cinema.films.microservice.exceptions.services.directors.DirectorCreationException;
import com.cinema.films.microservice.exceptions.services.directors.DirectorDeletingException;
import com.cinema.films.microservice.exceptions.services.directors.DirectorNotFoundException;
import com.cinema.films.microservice.exceptions.services.directors.DirectorUpdatingException;
import com.cinema.films.microservice.models.Director;

import java.util.List;

public interface DirectorsService {
    List<DirectorDto> getAllDirectors();
    DirectorDto getDirector(Long id) throws DirectorNotFoundException;
    DirectorDto createDirector(Director director) throws DirectorCreationException;
    DirectorDto updateDirector(Director director) throws DirectorNotFoundException, DirectorUpdatingException;
    Long deleteDirector(Long id) throws DirectorNotFoundException, DirectorDeletingException;
}
