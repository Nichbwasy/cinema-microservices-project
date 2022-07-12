package com.cinema.cinema.project.cinemas.microservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @OneToOne(targetEntity = SitType.class, fetch = FetchType.LAZY)
    private SitType type;

    @ToString.Exclude
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(targetEntity = Hall.class, fetch = FetchType.LAZY)
    private Hall hall;

    public Sit(Integer row, Integer place, SitType type, Hall hall) {
        this.row = row;
        this.place = place;
        this.type = type;
        this.hall = hall;
    }
}
