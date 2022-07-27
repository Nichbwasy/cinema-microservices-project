package com.cinema.films.microservice.repositories;

import com.cinema.films.microservice.models.Director;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorsRepository extends JpaRepository<Director, Long> {
    Boolean existsByFirstNameAndLastName(String firstName, String lastName);
}
