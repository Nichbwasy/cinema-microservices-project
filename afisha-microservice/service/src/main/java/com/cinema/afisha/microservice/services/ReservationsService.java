package com.cinema.afisha.microservice.services;

import com.cinema.afisha.microservice.domains.ReservationDto;
import com.cinema.afisha.microservice.services.dto.ReservationFormDto;

import java.util.List;

public interface ReservationsService {
    List<ReservationDto> getAllReservations();
    ReservationDto getReservation(Long id);
    ReservationDto getReservation(String email);
    ReservationDto createReservation(ReservationFormDto reservation);
    ReservationDto updateReservation(ReservationFormDto reservation);
    Long deleteReservation(Long id);
}
