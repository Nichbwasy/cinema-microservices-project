package com.cinema.cinemas.microservice.services;

import com.cinema.cinemas.microservice.domains.HallDto;
import com.cinema.cinemas.microservice.domains.SitDto;
import com.cinema.cinemas.microservice.exceptions.services.cinemas.CinemaNotFoundException;
import com.cinema.cinemas.microservice.exceptions.services.halls.HallCreationException;
import com.cinema.cinemas.microservice.exceptions.services.halls.HallDeletingException;
import com.cinema.cinemas.microservice.exceptions.services.halls.HallNotFoundException;
import com.cinema.cinemas.microservice.exceptions.services.halls.HallUpdatingException;
import com.cinema.cinemas.microservice.exceptions.services.sits.SitDeletingException;
import com.cinema.cinemas.microservice.models.Hall;

import java.util.List;

public interface HallsService {

    /**
     * Returns all halls which belongs to the cinema with id.
     * @param cinemaId Id of cinema to get all halls.
     * @return List of all halls DTOs.
     * @throws CinemaNotFoundException Throws when cinema with selected id not exist in repository.
     */
    List<HallDto> getAllHallsInCinema(Long cinemaId) throws CinemaNotFoundException;

    /**
     * Returns hall if it belong to the cinema.
     * @param cinemaId Id of cinema.
     * @param hallId Id of hall.
     * @return DTO of hall.
     * @throws CinemaNotFoundException Throws when cinema with selected id not exist in repository.
     * @throws HallNotFoundException Throws when hall doesn't belong to the cinema or hall not exist.
     */
    HallDto getCinemaHall(Long cinemaId, Long hallId) throws CinemaNotFoundException, HallNotFoundException;

    /**
     * Creates a new hall.
     * @param sitsTypeId Id of sits type in hall.
     * @param hall Hall data.
     * @return DTO of created hall.
     * @throws HallCreationException Throws if any exception occurred while saving a new hall.
     */
    HallDto createHall(Long sitsTypeId, Hall hall) throws HallCreationException;

    /**
     * Updates existed hall.
     * @param hall Updated hall data.
     * @return DTO of updated hall.
     * @throws HallNotFoundException Throws when hall not exist in repository.
     * @throws HallUpdatingException Throws if any exception occurred while updating a hall.
     */
    HallDto updateHall(Hall hall) throws HallNotFoundException, HallUpdatingException;

    /**
     * Removes selected hall from the cinema.
     * @param cinemaId Id of cinema.
     * @param hallId Id of hall to delete.
     * @return Id of deleted hall.
     * @throws CinemaNotFoundException Throws when cinema with selected id not exist in repository.
     * @throws HallNotFoundException Throws when hall doesn't belong to the cinema or hall not exist.
     * @throws HallDeletingException Throws if any exception occurred while deleting a hall.
     */
    Long deleteHallFromCinema(Long cinemaId, Long hallId) throws CinemaNotFoundException, HallNotFoundException, HallDeletingException;

    /**
     * Removes all halls from th
      * @param cinemaId Id of cinema.
     * @return List of all deleted halls id's
     * @throws CinemaNotFoundException Throws when cinema with selected id not exist in repository.
     * @throws HallDeletingException Throws if any exception occurred while deleting a halls.
     */
    List<Long> deleteAllHallsFromCinema(Long cinemaId) throws CinemaNotFoundException, HallDeletingException;

    /**
     * Returns all sits in hall.
     * @param hallId Id of hall.
     * @return List of hall sits DTO's.
     * @throws HallNotFoundException Throws when hall with selected id not exist in repository.
     */
    List<SitDto> getAllSitsInHall(Long hallId) throws HallNotFoundException;
}
