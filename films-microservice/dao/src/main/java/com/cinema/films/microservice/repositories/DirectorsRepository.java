package com.cinema.films.microservice.repositories;

import com.cinema.films.microservice.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorsRepository extends JpaRepository<Genre, Long> {
}
