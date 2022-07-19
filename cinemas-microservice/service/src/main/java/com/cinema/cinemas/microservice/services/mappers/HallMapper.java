package com.cinema.cinemas.microservice.services.mappers;

import com.cinema.cinemas.microservice.domains.HallDto;
import com.cinema.cinemas.microservice.models.Hall;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface HallMapper {

    HallMapper INSTANCE = Mappers.getMapper(HallMapper.class);

    HallDto mapToDto(Hall hall);

}
