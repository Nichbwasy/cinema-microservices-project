package com.cinema.cinemas.microservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sits")
public class Sit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Row is mandatory!")
    @Min(value = 0, message = "Row can't be lesser than 0!")
    @Column(name = "row", nullable = false)
    private Integer row;

    @NotNull(message = "Place is mandatory!")
    @Min(value = 0, message = "Place can't be lesser than 0!")
    @Column(name = "place", nullable = false)
    private Integer place;

    @OneToOne(targetEntity = SitType.class, fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private SitType type;

    public Sit(Integer row, Integer place, SitType type) {
        this.row = row;
        this.place = place;
        this.type = type;
    }
}
