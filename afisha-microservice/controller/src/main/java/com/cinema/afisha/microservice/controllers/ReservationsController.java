package com.cinema.afisha.microservice.controllers;

import com.cinema.afisha.microservice.domains.ReservationDto;
import com.cinema.afisha.microservice.services.ReservationsService;
import com.cinema.afisha.microservice.services.dto.ReservationFormDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/afisha/seance/{seanceId}")
public class ReservationsController {

    @Autowired
    private ReservationsService reservationsService;

    @PostMapping("/reservation")
    public ResponseEntity<ReservationDto> createReservation(@ModelAttribute ReservationFormDto reservation) {
        ReservationDto reservationDto = reservationsService.createReservation(reservation);
        log.info("New reservation has been created: {}", reservationDto);
        return ResponseEntity.ok().body(reservationDto);
    }

    @DeleteMapping("/reservation/{reservationId}")
    public ResponseEntity<Long> deleteReservation(@PathVariable Long reservationId) {
        Long deletedId = reservationsService.deleteReservation(reservationId);
        log.info("Reservation with id '{}' has been deleted successfully.", deletedId);
        return ResponseEntity.ok().body(deletedId);
    }

}
