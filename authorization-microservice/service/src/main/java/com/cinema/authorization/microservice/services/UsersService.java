package com.cinema.authorization.microservice.services;

import com.cinema.authorization.microservice.domain.UserDto;

public interface UsersService {
    UserDto getUserByUsername(String username);
    UserDto getUserByEmail(String email);
    Boolean checkUserPassword(String username, String password);
    Boolean existByUsername(String username);
    Boolean existByEmail(String email);
}
