package com.cinema.cinema.project.cinemas.microservice.models;

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
@Entity(name = "sits_types")
public class SitType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Sit type name is mandatory!")
    @Size(min = 5, max = 256, message = "Sit type name can't be lesser than 5 and more then 256 symbols!")
    @Column(name = "name", length = 256, nullable = false, unique = true)
    private String name;

    @Size(min = 5, max = 2048, message = "Sit type description can't be lesser than 5 and more then 2048 symbols!")
    @Column(name = "description", length = 2048)
    private String description;

    @Min(value = 1, message = "Sit type capacity can't be lesser than 1!")
    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    public SitType (String name, String description, Integer capacity) {
        this.name = name;
        this.description = description;
        this.capacity = capacity;
    }
}
