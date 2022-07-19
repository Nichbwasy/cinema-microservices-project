package com.cinema.films.microservice.repositories;

import com.cinema.films.microservice.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenresRepository extends JpaRepository<Genre, Long> {
}
