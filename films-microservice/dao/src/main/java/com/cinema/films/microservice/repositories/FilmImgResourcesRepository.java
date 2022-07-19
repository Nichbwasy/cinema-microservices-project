package com.cinema.films.microservice.repositories;

import com.cinema.films.microservice.models.FilmImgResource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmImgResourcesRepository extends JpaRepository<FilmImgResource, Long> {
}
