package com.cinema.cinema.project.cinemas.microservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "halls")
public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 128, nullable = false)
    @Size(min = 5, max = 128, message = "Cinema name is mandatory!")
    private String name;

    @Column(name = "description", length = 2048)
    @Size(min = 5, max = 2048, message = "Hall description can't be lesser than 5 and more then 2048 symbols!")
    private String description;

    @Min(value = 0, message = "Hall size can't be lesser than 0!")
    @Column(name = "size")
    private Integer size;

    @NotNull
    @Min(value = 0, message = "Count of row can't be lesser than 0!")
    @Column(name = "rows", nullable = false)
    private Integer rows;

    @NotNull(message = "Count of places in a row is mandatory!")
    @Min(value = 0, message = "Count of places in row can't be lesser than 0!")
    @Column(name = "places_in_row", nullable = false)
    private Integer placesInRow;

    @OneToMany(targetEntity = Sit.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "halls_sits",
            joinColumns = @JoinColumn(name =  "hall_id"),
            inverseJoinColumns = @JoinColumn(name = "sit_id")
    )
    private List<Sit> sits;

    public Hall(String name, String description, Integer rows, Integer placesInRow) {
        this.name = name;
        this.description = description;
        this.rows = rows;
        this.placesInRow = placesInRow;
        this.size = rows * placesInRow;
        this.sits = new ArrayList<>();
    }
}
