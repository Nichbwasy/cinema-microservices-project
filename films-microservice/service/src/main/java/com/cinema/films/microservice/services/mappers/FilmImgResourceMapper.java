package com.cinema.films.microservice.services.mappers;

import com.cinema.films.microservice.domains.FilmImgResourceDto;
import com.cinema.films.microservice.models.FilmImgResource;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FilmImgResourceMapper {

    FilmImgResourceMapper INSTANCE = Mappers.getMapper(FilmImgResourceMapper.class);

    FilmImgResourceDto mapToDto(FilmImgResource filmImgResource);

}
