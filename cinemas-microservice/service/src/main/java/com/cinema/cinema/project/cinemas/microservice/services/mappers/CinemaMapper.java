package com.cinema.cinema.project.cinemas.microservice.services.mappers;

import com.cinema.cinema.project.cinemas.microservice.domains.CinemaDto;
import com.cinema.cinema.project.cinemas.microservice.models.Cinema;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CinemaMapper {

    CinemaMapper INSTANCE = Mappers.getMapper(CinemaMapper.class);

    CinemaDto mapToDto(Cinema cinema);

}
