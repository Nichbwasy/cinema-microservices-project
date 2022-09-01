package com.cinema.authorization.microservice.services.mappers;

import com.cinema.authorization.microservice.models.User;
import com.cinema.authorization.microservice.services.dto.RegisterFormDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RegisterFormDtoMapper {

    RegisterFormDtoMapper INSTANCE = Mappers.getMapper(RegisterFormDtoMapper.class);

    User mapToModel(RegisterFormDto form);

}
