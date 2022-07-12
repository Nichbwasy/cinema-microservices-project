package com.cinema.cinema.project.cinemas.microservice.services;

import com.cinema.cinema.project.cinemas.microservice.domains.HallDto;
import com.cinema.cinema.project.cinemas.microservice.domains.SitDto;
import com.cinema.cinema.project.cinemas.microservice.models.Hall;

import java.util.List;

public interface HallsService {
    List<HallDto> getAllHallsInCinema(Long cinemaId);
    HallDto getHall(Long id);
    HallDto createHall(Long sitsTypeId, Hall hall);
    HallDto updateHall(Hall hall);
    Long deleteHall(Long id);
    List<Long> deleteAllHallsFromCinema(Long cinemaId);
    List<SitDto> getAllSitsInHall(Long hallId);
}
