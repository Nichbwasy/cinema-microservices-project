package com.cinema.cinema.project.cinemas.microservice.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SitDto {
    private Long id;
    private Integer row;
    private Integer place;
    private SitTypeDto type;
    private HallDto hall;

}
