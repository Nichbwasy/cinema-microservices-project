package com.cinema.authorization.microservice.services;

import com.cinema.authorization.microservice.domain.UserDto;
import com.cinema.authorization.microservice.services.dto.RegisterFormDto;

public interface RegistrationService {
    UserDto registerUser(RegisterFormDto form);
}
