package com.cinema.films.microservice.services;

import com.cinema.films.microservice.domains.DirectorDto;
import com.cinema.films.microservice.exceptions.services.directors.*;
import com.cinema.films.microservice.models.Director;

import java.util.List;

public interface DirectorsService {

    /**
     * Returns all film directors from repository.
     * @return List of directors DTO's
     */
    List<DirectorDto> getAllDirectors();

    /**
     * Returns film director by id from repository.
     * @param id Id of director.
     * @return DTO of film director.
     * @throws DirectorNotFoundException Throws when director with selected id not found in repository.
     */
    DirectorDto getDirector(Long id) throws DirectorNotFoundException;

    /**
     * Creates a new film director in repository.
     * @param director Director data
     * @return DTO of director.
     * @throws DirectorAlreadyExistException Throws if director with the same first name and las tame exist in repository.
     * @throws DirectorCreationException Throws if any exception occurred while creating a new director.
     */
    DirectorDto createDirector(Director director) throws DirectorAlreadyExistException, DirectorCreationException;

    /**
     * Updates existed film director data in repository.
     * @param director Updated director data.
     * @return Updated director DTO
     * @throws DirectorNotFoundException Throws when director with selected id not found in repository.
     * @throws DirectorUpdatingException Throws if any exception occurred while updating a director.
     */
    DirectorDto updateDirector(Director director) throws DirectorNotFoundException, DirectorUpdatingException;

    /**
     * Removing film director form repository by id.
     * @param id Id of director.
     * @return Id of deleted director.
     * @throws DirectorNotFoundException Throws when director with selected id not found in repository.
     * @throws DirectorDeletingException Throws if any exception occurred while updating a director.
     */
    Long deleteDirector(Long id) throws DirectorNotFoundException, DirectorDeletingException;
}
