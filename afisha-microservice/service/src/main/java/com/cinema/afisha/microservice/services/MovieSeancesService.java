package com.cinema.afisha.microservice.services;

import com.cinema.afisha.microservice.domains.MovieSeanceDto;
import com.cinema.afisha.microservice.models.MovieSeance;
import com.cinema.afisha.microservice.services.dto.SeanceDto;

import java.util.List;

public interface MovieSeancesService {
    List<SeanceDto> getPageOfSeances(Integer page);
    SeanceDto getSeance(Long id);
    MovieSeanceDto createSeance(MovieSeance movieSeance);
    MovieSeanceDto updateSeance(MovieSeance movieSeance);
    Long deleteSeance(Long id);
}
