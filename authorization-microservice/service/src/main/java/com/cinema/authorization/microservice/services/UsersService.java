package com.cinema.authorization.microservice.services;

import com.cinema.authorization.microservice.domain.UserDto;

public interface UsersService {
    UserDto getUserByUsername(String username);
    UserDto getUserByEmail(String email);
}
