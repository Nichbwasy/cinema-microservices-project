package com.cinema.cinema.project.cinemas.microservice.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HallDto {
    private Long id;
    private String name;
    private String description;
    private Integer size;
    private Integer rows;
    private Integer placesInRow;
    private List<SitDto> sits;

}
