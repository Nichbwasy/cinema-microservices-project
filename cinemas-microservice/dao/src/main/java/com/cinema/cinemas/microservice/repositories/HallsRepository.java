package com.cinema.cinemas.microservice.repositories;

import com.cinema.cinemas.microservice.models.Hall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HallsRepository extends JpaRepository<Hall, Long> {
}
