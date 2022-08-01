package com.cinema.afisha.microservice.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieSeanceSitDto {
    private Long id;
    private Long sitId;
    private String status;
    private Long filmId;
}
