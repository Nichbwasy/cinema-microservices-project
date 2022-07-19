package com.cinema.films.microservice.services.mappers;

import com.cinema.films.microservice.domains.DirectorDto;
import com.cinema.films.microservice.models.Director;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DirectorMapper {

        DirectorMapper INSTANCE = Mappers.getMapper(DirectorMapper.class);

        DirectorDto mapToDto(Director director);

}
