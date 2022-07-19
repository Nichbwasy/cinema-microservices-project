package com.cinema.films.microservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Genre name can't be null!")
    @Size(max = 64, message = "Genre name must not contain more than 64 characters! ")
    @Column(name = "name", length = 64, nullable = false, unique = true)
    private String name;

}
