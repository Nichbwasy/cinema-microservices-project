package com.cinema.films.microservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "directors")
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Director's first name cant be null!")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull(message = "Director's last name cant be null!")
    @Column(name = "last_name", nullable = false)
    private String lastName;
}
