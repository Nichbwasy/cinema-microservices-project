package com.cinema.cinema.project.cinemas.microservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "cinemas")
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 128, nullable = false, unique = true)
    @Size(min = 5, max = 128, message = "Cinema name can't be lesser than 5 and more then 128 symbols!")
    private String name;

    @Column(name = "description", length = 2048)
    @Size(min = 5, max = 2048, message = "Cinema description can't be lesser than 5 and more then 2048 symbols!")
    private String description;

    @OneToMany(targetEntity = Hall.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "cinemas_halls",
            joinColumns = @JoinColumn(name =  "cinema_id"),
            inverseJoinColumns = @JoinColumn(name = "hall_id")
    )
    private List<Hall> halls;

    public Cinema(String name, String description) {
        this.name = name;
        this.description = description;
        this.halls = new ArrayList<>();
    }
}
