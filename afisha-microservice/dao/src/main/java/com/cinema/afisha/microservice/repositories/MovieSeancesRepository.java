package com.cinema.afisha.microservice.repositories;

import com.cinema.afisha.microservice.models.MovieSeance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieSeancesRepository extends JpaRepository<MovieSeance, Long> {
}
