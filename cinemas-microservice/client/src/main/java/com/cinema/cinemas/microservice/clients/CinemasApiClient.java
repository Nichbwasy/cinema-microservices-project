package com.cinema.cinemas.microservice.clients;

import com.cinema.cinemas.microservice.domains.CinemaDto;
import com.cinema.cinemas.microservice.domains.HallDto;
import com.cinema.cinemas.microservice.exceptions.clients.CinemasMicroserviceApiException;

import java.util.List;

public interface CinemasApiClient {

    /**
     * Get a cinema by id from CINEMAS-MICROSERVICE.
     * @param id Id of cinema.
     * @return DTO of cinema.
     * @throws CinemasMicroserviceApiException Throws when any exception was occurred in API client.
     */
    CinemaDto getCinema(Long id) throws CinemasMicroserviceApiException;

    /**
     * Get a hall by id if its belongs to cinema from CINEMAS-MICROSERVICE.
     * @param cinemaId Id of cinema.
     * @param hallId Id of hall.
     * @return DTO of hall.
     * @throws CinemasMicroserviceApiException Throws when any exception was occurred in API client.
     */
    HallDto getCinemaHall(Long cinemaId, Long hallId) throws CinemasMicroserviceApiException;

    /**
     * Gets all halls in cinema by id from CINEMAS-MICROSERVICE.
     * @param cinemaId Id of cinema.
     * @return List of all halls in cinema.
     * @throws CinemasMicroserviceApiException Throws when any exception was occurred in API client.
     */
    List<HallDto> getCinemaHalls(Long cinemaId) throws CinemasMicroserviceApiException;
}
