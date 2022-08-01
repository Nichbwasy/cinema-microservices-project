package com.cinema.afisha.microservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "movie_seances")
public class MovieSeance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Code for movie seance can't be null!")
    @Size(min = 16, max = 16, message = "Code for movie seance must contains 16 characters!")
    @Column(name = "code", length = 16, nullable = false, unique = true)
    private String code;

    @NotNull(message = "Begin time for a movie seance can't be null!")
    @Column(name = "beginTime", nullable = false)
    private Date beginTime;

    @NotNull(message = "End time for a movie seance can't be null!")
    @Column(name = "endTime", nullable = false)
    private Date endTime;

    @Min(value = 0, message = "Price can't be negative!")
    @NotNull(message = "Price for a movie seance is mandatory!")
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Min(value = 0, message = "Cinema id can't be negative!")
    @NotNull(message = "Cinema id a movie seance is mandatory!")
    @Column(name = "cinemaId", nullable = false)
    private Long cinemaId;

    @Min(value = 0, message = "Hall id can't be negative!")
    @NotNull(message = "Hall id a movie seance is mandatory!")
    @Column(name = "hallId", nullable = false)
    private Long hallId;

    @Min(value = 0, message = "Film id can't be negative!")
    @NotNull(message = "Film id a movie seance is mandatory!")
    @Column(name = "filId", nullable = false)
    private Long filmId;

    @OneToMany(targetEntity = MovieSeanceSit.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(name = "seance_sits",
            joinColumns = @JoinColumn(name =  "seance_id"),
            inverseJoinColumns = @JoinColumn(name = "sit_id")
    )
    private List<MovieSeanceSit> sits;

}
