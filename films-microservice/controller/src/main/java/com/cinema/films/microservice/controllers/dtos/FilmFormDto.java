package com.cinema.films.microservice.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmFormDto {
    private Long id;
    private String name;
    private String description;
    private Integer year;
    private Long genreId;
    private Long directorId;
}
