package com.cinema.cinemas.microservice.services;

import com.cinema.cinemas.microservice.domains.CinemaDto;
import com.cinema.cinemas.microservice.domains.HallDto;
import com.cinema.cinemas.microservice.exceptions.services.cinemas.*;
import com.cinema.cinemas.microservice.exceptions.services.halls.HallCreationException;
import com.cinema.cinemas.microservice.exceptions.services.sits.types.SitTypeNotFoundException;
import com.cinema.cinemas.microservice.models.Cinema;
import com.cinema.cinemas.microservice.models.Hall;

import java.util.List;

public interface CinemasService {
    /**
     * Returns list of all cinemas in repository.
     * @return List of all existed cinemas.
     */
    List<CinemaDto> getAllCinemas();

    /**
     * Returns cinema by id from repository.
     * @param id Id of cinema to return.
     * @return DTO of cinema.
     * @throws CinemaNotFoundException Throws when cinema with selected id not exist in repository.
     */
    CinemaDto getCinema(Long id) throws CinemaNotFoundException;

    /**
     * Creates a new cinema in repository.
     * @param cinema Cinema data.
     * @return DTO of created cinema.
     * @throws CinemaAlreadyExistException Throws when cinema name already exist in repository.
     * @throws CinemaCreationException Throws if any exception occurred while saving a new cinema.
     */
    CinemaDto createCinema(Cinema cinema) throws CinemaAlreadyExistException, CinemaCreationException;

    /**
     * Updates a cinema record in repository.
     * @param cinema Updated cinema data.
     * @return DTO of updated cinema.
     * @throws CinemaNotFoundException Throws when cinema with selected id not exist in repository.
     * @throws CinemaUpdatingException Throws if any exception occurred while updating a cinema.
     */
    CinemaDto updateCinema(Cinema cinema) throws CinemaNotFoundException, CinemaUpdatingException;

    /**
     * Removes a cinema records from repository.
     * @param ids Ids of cinemas to delete.
     * @return List of deleted cinemas ids.
     * @throws CinemaDeletingException Throws if any exception occurred while deleting cinemas.
     */
    List<Long> deleteCinemasByIds(List<Long> ids) throws CinemaDeletingException;

    /**
     * Creates a new hall to existed cinema.
     * @param cinemaId Id of cinema to add hall.
     * @param sitTypeId Type of sits id in created hall.
     * @param hall Hall data.
     * @return DTO of created hall.
     * @throws CinemaNotFoundException Throws when cinema with selected id not exist in repository.
     * @throws SitTypeNotFoundException Throws when sit type with selected id not exist in repository.
     * @throws HallCreationException Throws if any exception occurred while saving a new hall to the cinema.
     */
    HallDto addHall(Long cinemaId, Long sitTypeId, Hall hall) throws CinemaNotFoundException, SitTypeNotFoundException, HallCreationException;
}
