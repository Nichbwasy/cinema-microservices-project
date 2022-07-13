package com.cinema.cinema.project.cinemas.microservice.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CinemaDto {
    private Long id;
    private String name;
    private String description;
    private List<HallDto> halls;

    public CinemaDto(String name, String description) {
        this.name = name;
        this.description = description;
        this.halls = new ArrayList<>();
    }
}
