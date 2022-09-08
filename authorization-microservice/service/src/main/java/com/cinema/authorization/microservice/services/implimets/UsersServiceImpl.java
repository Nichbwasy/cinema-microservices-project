package com.cinema.authorization.microservice.services.implimets;

import com.cinema.authorization.microservice.domain.UserDto;
import com.cinema.authorization.microservice.exceptions.services.users.UserNotFoundException;
import com.cinema.authorization.microservice.models.User;
import com.cinema.authorization.microservice.repositories.UsersRepository;
import com.cinema.authorization.microservice.services.UsersService;
import com.cinema.authorization.microservice.services.mappers.UserMapper;
import com.cinema.common.utils.generators.encoders.EncoderGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDto getUserByUsername(String username) {
        if (usersRepository.existsByUsername(username)) {
            User user = usersRepository.getByUsername(username);
            log.info("User with username '{}' has been found.", username);
            return UserMapper.INSTANCE.mapToDto(user);
        } else {
            log.warn("Can't find user with username '{}'!", username);
            throw new UserNotFoundException(String.format("Can't find user with username '%s'!", username));
        }
    }

    @Override
    public UserDto getUserByEmail(String email) {
        if (usersRepository.existsByEmail(email)) {
            User user = usersRepository.getByUsername(email);
            log.info("User with email '{}' has been found.", email);
            return UserMapper.INSTANCE.mapToDto(user);
        } else {
            log.warn("Can't find user with email '{}'!", email);
            throw new UserNotFoundException(String.format("Can't find user with email '%s'!", email));
        }
    }

    @Override
    public Boolean checkUserPassword(String username, String password) {
        if (usersRepository.existsByUsername(username)) {
            User user = usersRepository.getByUsername(username);
            String encPassword = EncoderGenerator.getBCryptPasswordEncoder().encode(password);
            if (EncoderGenerator.getBCryptPasswordEncoder().matches(password, user.getPassword())) {
                log.info("Password for user '{}' match.", username);
                return true;
            } else {
                log.info("Password for user '{}' doesn't match!", username);
                return false;
            }
        } else {
            log.warn("Can't find user with username '{}'!", username);
            throw new UserNotFoundException(String.format("Can't find user with username '%s'!", username));
        }
    }

    @Override
    public Boolean existByUsername(String username) {
        return usersRepository.existsByUsername(username);
    }

    @Override
    public Boolean existByEmail(String email) {
        return usersRepository.existsByEmail(email);
    }
}
