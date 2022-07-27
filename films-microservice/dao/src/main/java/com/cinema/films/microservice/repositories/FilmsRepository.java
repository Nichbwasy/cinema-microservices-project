package com.cinema.films.microservice.repositories;

import com.cinema.films.microservice.models.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmsRepository extends JpaRepository<Film, Long> {
    Boolean existsByName(String name);
}
