package com.cinema.films.microservice.services;

import com.cinema.films.microservice.domains.GenreDto;
import com.cinema.films.microservice.exceptions.services.directors.*;
import com.cinema.films.microservice.exceptions.services.genres.*;
import com.cinema.films.microservice.models.Genre;

import java.util.List;

public interface GenresService {

    /**
     * Returns all film genres from repository.
     * @return List of genres DTO's
     */
    List<GenreDto> getAllGenres();

    /**
     * Returns genre by id from repository.
     * @param id Id of genre.
     * @return DTO of genre.
     * @throws DirectorNotFoundException Throws when genre with selected id not found in repository.
     */
    GenreDto getGenre(Long id) throws GenreNotFoundException;

    /**
     * Creates a new genre in repository.
     * @param genre Genre data
     * @return DTO of genre.
     * @throws DirectorAlreadyExistException Throws if genre with the same name exist in repository.
     * @throws DirectorCreationException Throws if any exception occurred while creating a new genre.
     */
    GenreDto createGenre(Genre genre) throws GenreAlreadyExistException, GenreCreationException;

    /**
     * Updates existed genre data in repository.
     * @param genre Updated genre data.
     * @return Updated genre DTO
     * @throws DirectorNotFoundException Throws when genre with selected id not found in repository.
     * @throws DirectorUpdatingException Throws if any exception occurred while updating a genre.
     */
    GenreDto updateGenre(Genre genre) throws GenreNotFoundException, GenreUpdatingException;

    /**
     * Removing genre form repository by id.
     * @param id Id of genre.
     * @return Id of deleted genre.
     * @throws DirectorNotFoundException Throws when genre with selected id not found in repository.
     * @throws DirectorDeletingException Throws if any exception occurred while updating a genre.
     */
    Long deleteGenre(Long id) throws GenreNotFoundException, GenreDeletingException;
}
