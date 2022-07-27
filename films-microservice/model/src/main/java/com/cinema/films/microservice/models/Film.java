package com.cinema.films.microservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "films")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Film name can't be null!")
    @Size(min = 3, max = 256, message = "Film name must contain 5-256 characters!")
    @Column(name = "name", length = 256, nullable = false, unique = true)
    private String name;

    @NotNull(message = "Film description can't be null!")
    @Size(min = 5, max = 2048, message = "Film description must contain 5-2048 characters!")
    @Column(name = "description", length = 2048)
    private String description;

    @NotNull(message = "Film year can't be null!")
    @Min(value = 1800, message = "Film year can't be lesser than 1800!")
    @Column(name = "year", nullable = false)
    private Integer year;

    @OneToOne(targetEntity = Genre.class, fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private Genre genre;

    @OneToOne(targetEntity = Director.class, fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private Director director;

    @ToString.Exclude
    @OneToOne(targetEntity = FilmImgResource.class, fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private FilmImgResource imgResource;
}
