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
    List<CinemaDto> getAllCinemas();
    CinemaDto getCinema(Long id) throws CinemaNotFoundException;
    CinemaDto createCinema(Cinema cinema) throws CinemaAlreadyExistException, CinemaCreationException;
    CinemaDto updateCinema(Cinema cinema) throws CinemaNotFoundException, CinemaUpdatingException;
    List<Long> deleteCinemasByIds(List<Long> ids) throws CinemaDeletingException;
    HallDto addHall(Long cinemaId, Long sitTypeId, Hall hall) throws CinemaNotFoundException, SitTypeNotFoundException, HallCreationException;
}
