package com.cinema.authorization.microservice.services.implimets;

import com.cinema.authorization.microservice.domain.UserDto;
import com.cinema.authorization.microservice.exceptions.services.matching.PasswordsMatchException;
import com.cinema.authorization.microservice.exceptions.services.users.UserAlreadyExistException;
import com.cinema.authorization.microservice.exceptions.services.users.UserCreationException;
import com.cinema.authorization.microservice.models.Role;
import com.cinema.authorization.microservice.models.User;
import com.cinema.authorization.microservice.repositories.RolesRepository;
import com.cinema.authorization.microservice.repositories.UsersRepository;
import com.cinema.authorization.microservice.services.RegistrationService;
import com.cinema.authorization.microservice.services.dto.RegisterFormDto;
import com.cinema.authorization.microservice.services.mappers.UserMapper;
import com.cinema.authorization.microservice.services.mappers.RegisterFormDtoMapper;
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
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Override
    public UserDto registerUser(RegisterFormDto form) {
        if (!usersRepository.existsByUsernameOrEmail(form.getUsername(), form.getEmail())) {
            if (form.getPassword().equals(form.getRepeatPassword())) {
                try {
                    //Encrypt password
                    form.setPassword(EncoderGenerator.getBCryptPasswordEncoder().encode(form.getPassword()));
                    User user = new User(form.getUsername(), form.getEmail(), form.getPassword());
                    Role role = rolesRepository.getByName(UserRoles.USER);
                    user.setProvider(ProviderTypes.LOCAL);
                    user.setRoles(Collections.singletonList(role));
                    user.setEnabled(true);

                    user = usersRepository.save(user);
                    log.info("New user '{}' has been registered.", user.getEmail());
                    return UserMapper.INSTANCE.mapToDto(user);
                } catch (Exception e) {
                    log.error("Exception was occurred while creation a new user!");
                    throw new UserCreationException("Exception was occurred while creation a nre user!");
                }
            } else {
                log.info("Passwords doesn't match!");
                throw new PasswordsMatchException("Passwords doesn't match!");
            }
        } else {
            log.warn("User with the same username '{}' or email '{}' already exists!", form.getUsername(), form.getEmail());
            throw new UserAlreadyExistException(
                    String.format("User with the same username '%s' or email '%s' already exists!", form.getUsername(), form.getEmail())
            );
        }
    }
}
