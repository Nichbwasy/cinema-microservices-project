package com.cinema.cinemas.microservice.clients;

import com.cinema.cinemas.microservice.domains.CinemaDto;
import com.cinema.cinemas.microservice.domains.HallDto;

import java.util.List;

public interface CinemasApiClient {
    CinemaDto getCinema(Long id);
    HallDto getCinemaHall(Long cinemaId, Long hallId);
    List<HallDto> getCinemaHalls(Long cinemaId);
}
