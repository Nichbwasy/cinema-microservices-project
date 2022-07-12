package com.cinema.cinema.project.cinemas.microservice.services;

import com.cinema.cinema.project.cinemas.microservice.domains.CinemaDto;
import com.cinema.cinema.project.cinemas.microservice.domains.HallDto;
import com.cinema.cinema.project.cinemas.microservice.exceptions.services.cinemas.*;
import com.cinema.cinema.project.cinemas.microservice.exceptions.services.halls.HallCreationException;
import com.cinema.cinema.project.cinemas.microservice.exceptions.services.sits.types.SitTypeNotFoundException;
import com.cinema.cinema.project.cinemas.microservice.models.Cinema;
import com.cinema.cinema.project.cinemas.microservice.models.Hall;

import java.util.List;

public interface CinemasService {
    List<CinemaDto> getAllCinemas();
    CinemaDto getCinema(Long id) throws CinemaNotFoundException;
    CinemaDto createCinema(Cinema cinema) throws CinemaAlreadyExistException, CinemaCreationException;
    CinemaDto updateCinema(Cinema cinema) throws CinemaNotFoundException, CinemaUpdatingException;
    List<Long> deleteCinemasByIds(List<Long> ids) throws CinemaDeletingException;
    HallDto addHall(Long cinemaId, Long sitTypeId, Hall hall) throws CinemaNotFoundException, SitTypeNotFoundException, HallCreationException;
}
