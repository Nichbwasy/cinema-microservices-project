package com.cinema.cinema.project.cinemas.microservice.repositories;

import com.cinema.cinema.project.cinemas.microservice.models.SitType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SitsTypesRepository extends JpaRepository<SitType, Long> {
    SitType getByName(String name);
    Boolean existsByName(String name);
}
