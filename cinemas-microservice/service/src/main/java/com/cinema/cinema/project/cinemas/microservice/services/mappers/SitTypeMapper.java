package com.cinema.cinema.project.cinemas.microservice.services.mappers;

import com.cinema.cinema.project.cinemas.microservice.domains.SitTypeDto;
import com.cinema.cinema.project.cinemas.microservice.models.SitType;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SitTypeMapper {

    SitTypeMapper INSTANCE = Mappers.getMapper(SitTypeMapper.class);

    SitTypeDto mapToDto(SitType sitType);
}
