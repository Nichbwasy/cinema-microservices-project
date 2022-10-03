package com.cinema.authorization.microservice.services;

import com.cinema.authorization.microservice.domain.UserDto;
import com.cinema.authorization.microservice.exceptions.services.matching.PasswordsMatchException;
import com.cinema.authorization.microservice.exceptions.services.users.UserAlreadyExistException;
import com.cinema.authorization.microservice.exceptions.services.users.UserCreationException;
import com.cinema.authorization.microservice.services.dto.RegisterFormDto;

public interface RegistrationService {
    /**
     * Register a new local user in database.
     * @param form Registration form DTO.
     * @return Returns DTO of created user.
     * @throws UserAlreadyExistException Throws when user with selected username or email already exist in database.
     * @throws PasswordsMatchException Throws when value in fields 'password' and 'repeatPassword' in registration form doesn't match.
     * @throws UserCreationException Throws if error was occurred while creating a new user.
     */
    UserDto registerUser(RegisterFormDto form) throws UserAlreadyExistException, PasswordsMatchException, UserCreationException;
}
