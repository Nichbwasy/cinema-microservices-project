package com.cinema.authorization.microservice.services.implimets;

import com.cinema.authorization.microservice.domain.UserDto;
import com.cinema.authorization.microservice.exceptions.services.users.UserNotFoundException;
import com.cinema.authorization.microservice.models.Role;
import com.cinema.authorization.microservice.models.User;
import com.cinema.authorization.microservice.repositories.RolesRepository;
import com.cinema.authorization.microservice.repositories.UsersRepository;
import com.cinema.authorization.microservice.services.UsersService;
import com.cinema.authorization.microservice.services.dto.oauth2.GoogleOAuth2User;
import com.cinema.authorization.microservice.services.mappers.UserMapper;
import com.cinema.common.utils.authorizations.providers.ProviderTypes;
import com.cinema.common.utils.authorizations.roles.UserRoles;
import com.cinema.common.utils.generators.encoders.EncoderGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

@Slf4j
@Service
@Transactional
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RolesRepository rolesRepository;

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
            User user = usersRepository.getByEmail(email);
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
    public UserDto processGoogleOAuthPostLogin(GoogleOAuth2User googleOAuth2User) {
        if (usersRepository.existsByEmail(googleOAuth2User.getEmail())) {
            log.info("Google user with email '{}' has been found.", googleOAuth2User.getEmail());
            User user = usersRepository.getByEmail(googleOAuth2User.getEmail());
            return UserMapper.INSTANCE.mapToDto(user);
        } else {
            Role role = rolesRepository.getByName(UserRoles.USER);
            User user = new User();
            user.setUsername(googleOAuth2User.getEmail());
            user.setEmail(googleOAuth2User.getEmail());
            user.setRoles(Collections.singletonList(role));
            user.setProvider(ProviderTypes.GOOGLE);
            user.setEnabled(true);
            user = usersRepository.save(user);
            log.info("New google user with email '{}' was created.", googleOAuth2User.getEmail());
            return UserMapper.INSTANCE.mapToDto(user);
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
