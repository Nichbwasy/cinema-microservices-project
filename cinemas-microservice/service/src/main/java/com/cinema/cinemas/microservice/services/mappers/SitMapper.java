package com.cinema.cinemas.microservice.services.mappers;

import com.cinema.cinemas.microservice.domains.SitDto;
import com.cinema.cinemas.microservice.models.Sit;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SitMapper {

    SitMapper INSTANCE = Mappers.getMapper(SitMapper.class);

    SitDto mapToDto(Sit sit);

}
