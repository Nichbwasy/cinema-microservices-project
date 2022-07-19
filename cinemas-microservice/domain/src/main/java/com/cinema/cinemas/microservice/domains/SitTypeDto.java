package com.cinema.cinemas.microservice.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SitTypeDto {
    private Long id;
    private String name;
    private String description;
    private Integer capacity;

    public SitTypeDto(String name, String description, Integer capacity) {
        this.name = name;
        this.description = description;
        this.capacity = capacity;
    }
}
