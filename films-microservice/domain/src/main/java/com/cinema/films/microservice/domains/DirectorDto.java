package com.cinema.films.microservice.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectorDto {
    private Long id;
    private String firstName;
    private String lastName;
}
