package com.cinema.afisha.microservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Email can't be null!")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-]+$", message = "Email is invalid!")
    @Size(max = 255, message = "Email must contains lesser than 255 characters!")
    @Column(name = "status", length = 255, nullable = false)
    private String reserverEmail;

    @NotNull(message = "Time od reservation can't be null!")
    @Column(name = "reservationTime", nullable = false)
    private Date reservationTime;

    @OneToOne(targetEntity = MovieSeance.class, fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private MovieSeance movieSeance;

    @OneToOne(targetEntity = MovieSeanceSit.class, fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private MovieSeanceSit sit;
}
