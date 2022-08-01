package com.cinema.afisha.microservice.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieSeanceDto {
    private Long id;
    private String code;
    private Date beginTime;
    private Date endTime;
    private BigDecimal price;
    private Long cinemaId;
    private Long hallId;
    private Long filmId;
    private List<MovieSeanceSitDto> sits;
}
