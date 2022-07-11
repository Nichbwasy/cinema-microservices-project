package com.cinema.cinema.project.cinemas.microservice.services;

import com.cinema.cinema.project.cinemas.microservice.domains.CinemaDto;
import com.cinema.cinema.project.cinemas.microservice.domains.HallDto;
import com.cinema.cinema.project.cinemas.microservice.models.Cinema;
import com.cinema.cinema.project.cinemas.microservice.models.Hall;

import java.util.List;

public interface CinemasService {
    List<CinemaDto> getAllCinemas();
    CinemaDto getCinema(Long id);
    CinemaDto createCinema(Cinema cinema);
    CinemaDto updateCinema(Cinema cinema);
    List<Long> deleteCinemasByIds(List<Long> ids);
    HallDto addHall(Long cinemaId, Hall hall);
}
