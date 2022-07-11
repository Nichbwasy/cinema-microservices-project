package com.cinema.cinema.project.cinemas.microservice.repositories;

import com.cinema.cinema.project.cinemas.microservice.models.Hall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HallsRepository extends JpaRepository<Hall, Long> {
}
