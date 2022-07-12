package com.cinema.cinema.project.cinemas.microservice.repositories;

import com.cinema.cinema.project.cinemas.microservice.models.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemasRepository extends JpaRepository<Cinema, Long> {
    Cinema getByName(String name);
    Boolean existsByName(String name);
}
