package com.cinema.films.microservice.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmDto {
    private Long id;
    private String name;
    private String description;
    private Integer year;
    private GenreDto genre;
    private DirectorDto director;
}
