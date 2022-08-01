package com.cinema.afisha.microservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "movie_seance_sits")
public class MovieSeanceSit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 0, message = "Sit id can't be negative!")
    @NotNull(message = "Sit id a movie seance is mandatory!")
    @Column(name = "sitId", nullable = false)
    private Long sitId;

    @NotNull(message = "Status for sit can't be null!")
    @Size(max = 64, message = "Sit status must contains lesser than 64 characters!")
    @Column(name = "status", length = 64, nullable = false)
    private String status;

    @Min(value = 0, message = "Film id can't be negative!")
    @NotNull(message = "Film id a movie seance is mandatory!")
    @Column(name = "filId", nullable = false)
    private Long filmId;
}
