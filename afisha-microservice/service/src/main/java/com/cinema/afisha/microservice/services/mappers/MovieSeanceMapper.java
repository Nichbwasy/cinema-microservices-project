package com.cinema.afisha.microservice.services.mappers;

import com.cinema.afisha.microservice.domains.MovieSeanceDto;
import com.cinema.afisha.microservice.models.MovieSeance;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MovieSeanceMapper {

    MovieSeanceMapper INSTANCE = Mappers.getMapper(MovieSeanceMapper.class);

    MovieSeanceDto mapToDto(MovieSeance movieSeance);

}
