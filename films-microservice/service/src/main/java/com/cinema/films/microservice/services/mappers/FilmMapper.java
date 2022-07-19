package com.cinema.films.microservice.services.mappers;

import com.cinema.films.microservice.domains.FilmDto;
import com.cinema.films.microservice.models.Film;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FilmMapper {

    FilmMapper INSTANCE = Mappers.getMapper(FilmMapper.class);

    FilmDto mapToDto(Film film);

}
