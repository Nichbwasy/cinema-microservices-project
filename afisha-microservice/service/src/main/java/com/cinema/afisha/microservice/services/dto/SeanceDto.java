package com.cinema.afisha.microservice.services.dto;

import com.cinema.afisha.microservice.domains.MovieSeanceDto;
import com.cinema.cinemas.microservice.domains.HallDto;
import com.cinema.films.microservice.domains.FilmDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeanceDto {
    private MovieSeanceDto movieSeance;
    private FilmDto film;
    private HallDto hall;
    private InputStream poster;
}
