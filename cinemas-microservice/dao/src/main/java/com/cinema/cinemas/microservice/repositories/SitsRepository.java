package com.cinema.cinemas.microservice.repositories;

import com.cinema.cinemas.microservice.models.Sit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SitsRepository extends JpaRepository<Sit, Long> {
}
