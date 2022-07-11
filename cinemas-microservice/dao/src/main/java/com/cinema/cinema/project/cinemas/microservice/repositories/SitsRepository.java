package com.cinema.cinema.project.cinemas.microservice.repositories;

import com.cinema.cinema.project.cinemas.microservice.models.Sit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SitsRepository extends JpaRepository<Sit, Long> {
}
