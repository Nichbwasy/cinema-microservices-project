package com.cinema.authorization.microservice.services.mappers;

import com.cinema.authorization.microservice.domain.UserDto;
import com.cinema.authorization.microservice.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto mapToDto(User user);

}
