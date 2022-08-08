package com.cinema.afisha.microservice.repositories;

import com.cinema.afisha.microservice.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationsRepository extends JpaRepository<Reservation, Long> {
    Boolean existsByReserverEmail(String reserverEmail);
    Reservation getByReserverEmail(String reserverEmail);

}
