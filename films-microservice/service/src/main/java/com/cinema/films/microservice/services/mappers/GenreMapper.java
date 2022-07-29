package com.cinema.films.microservice.services.mappers;

import com.cinema.films.microservice.domains.GenreDto;
import com.cinema.films.microservice.models.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    GenreMapper INSTANCE = Mappers.getMapper(GenreMapper.class);

    Genre mapToModel(GenreDto genre);
    GenreDto mapToDto (Genre genre);

}
