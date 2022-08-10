package com.cinema.afisha.microservice.services;

import com.cinema.afisha.microservice.domains.ReservationDto;
import com.cinema.afisha.microservice.exceptions.services.reservations.*;
import com.cinema.afisha.microservice.exceptions.services.seances.MovieSeanceNotFoundException;
import com.cinema.afisha.microservice.exceptions.services.seances.sits.MovieSeanceSitNotFoundException;
import com.cinema.afisha.microservice.services.dto.ReservationFormDto;

import java.util.List;

public interface ReservationsService {
    /**
     * Returns list of all reservations existed in repository.
     * @return List of existed reservations.
     */
    List<ReservationDto> getAllReservations();

    /**
     * Returns reservation by id from repository.
     * @param id Id of reservation to return.
     * @return DTO of reservation.
     * @throws ReservationNotFoundException Throws when reservation with selected id not exist in repository.
     */
    ReservationDto getReservation(Long id) throws ReservationNotFoundException;

    /**
     * Returns reservation by email from repository.
     * @param email Email of reservation to return.
     * @return DTO of reservation.
     * @throws ReservationNotFoundException Throws when reservation with selected email not exist in repository.
     */
    ReservationDto getReservation(String email) throws ReservationNotFoundException;

    /**
     * Reserve a sit for a movie seance. If sit is available.
     * @param reservation Reservation data.
     * @return DTO of created reservation.
     * @throws MovieSeanceNotFoundException Throws when movie seance with selected id not exist in repository.
     * @throws MovieSeanceSitNotFoundException Throws if selected sit not exist in repository.
     * @throws ReservationAlreadyExistException Throws if sit was already reserved.
     * @throws ReservationCreationException Throws if any exception occurred while saving a new reservation.
     */
    ReservationDto createReservation(ReservationFormDto reservation) throws MovieSeanceNotFoundException, MovieSeanceSitNotFoundException, ReservationAlreadyExistException, ReservationCreationException;

    /**
     * Updates reservation for a sit.
     * @param reservation Data of existed reservation.
     * @return DTO of updated reservation.
     * @throws ReservationNotFoundException Throws when reservation to updated not exists in repository.
     * @throws MovieSeanceNotFoundException Throws when movie seance with selected id not exist in repository.
     * @throws MovieSeanceSitNotFoundException Throws if selected sit not exist in repository.
     * @throws ReservationUpdatingException Throws if any exception occurred while updating a reservation.
     */
    ReservationDto updateReservation(ReservationFormDto reservation) throws ReservationNotFoundException, MovieSeanceNotFoundException, MovieSeanceSitNotFoundException, ReservationUpdatingException;

    /**
     * Deletes a reservation by id.
     * @param id Id of reservation to delete.
     * @return Id of deleted reservation.
     * @throws ReservationNotFoundException Throws when reservation to updated not exists in repository.
     * @throws ReservationDeletingException Throws if any exception occurred while deleting a reservation.
     */
    Long deleteReservation(Long id) throws ReservationNotFoundException, ReservationDeletingException;
}
