package com.cinema.cinemas.microservice.services;

import com.cinema.cinemas.microservice.domains.HallDto;
import com.cinema.cinemas.microservice.domains.SitDto;
import com.cinema.cinemas.microservice.exceptions.services.cinemas.CinemaNotFoundException;
import com.cinema.cinemas.microservice.exceptions.services.halls.HallCreationException;
import com.cinema.cinemas.microservice.exceptions.services.halls.HallDeletingException;
import com.cinema.cinemas.microservice.exceptions.services.halls.HallNotFoundException;
import com.cinema.cinemas.microservice.exceptions.services.sits.SitDeletingException;
import com.cinema.cinemas.microservice.models.Hall;

import java.util.List;

public interface HallsService {
    List<HallDto> getAllHallsInCinema(Long cinemaId) throws CinemaNotFoundException;
    HallDto getCinemaHall(Long cinemaId, Long hallId) throws CinemaNotFoundException, HallNotFoundException;
    HallDto createHall(Long sitsTypeId, Hall hall) throws HallCreationException;
    HallDto updateHall(Hall hall) throws HallNotFoundException, HallCreationException;
    Long deleteHallFromCinema(Long cinemaId, Long hallId) throws HallNotFoundException, HallDeletingException;
    List<Long> deleteAllHallsFromCinema(Long cinemaId) throws CinemaNotFoundException, HallDeletingException;
    List<SitDto> getAllSitsInHall(Long hallId) throws HallNotFoundException, SitDeletingException;
}
