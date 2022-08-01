package com.cinema.afisha.microservice.repositories;

import com.cinema.afisha.microservice.models.MovieSeanceSit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieSeanceSitsRepository extends JpaRepository<MovieSeanceSit, Long> {
}
