package com.cinema.authorization.microservice.services;

import com.cinema.authorization.microservice.domain.RoleDto;
import com.cinema.authorization.microservice.domain.UserDto;
import com.cinema.authorization.microservice.exceptions.services.matching.PasswordsMatchException;
import com.cinema.authorization.microservice.exceptions.services.users.UserAlreadyExistException;
import com.cinema.authorization.microservice.exceptions.services.users.UserCreationException;
import com.cinema.authorization.microservice.models.Role;
import com.cinema.authorization.microservice.models.User;
import com.cinema.authorization.microservice.repositories.RolesRepository;
import com.cinema.authorization.microservice.repositories.UsersRepository;
import com.cinema.authorization.microservice.services.config.RegistrationServiceTestsConfiguration;
import com.cinema.authorization.microservice.services.dto.RegisterFormDto;
import com.cinema.common.utils.authorizations.providers.ProviderTypes;
import com.cinema.common.utils.authorizations.roles.UserRoles;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.stream.Collectors;

@SpringBootTest(classes = {RegistrationService.class})
@Import(RegistrationServiceTestsConfiguration.class)
@ExtendWith(SpringExtension.class)
public class RegistrationServiceTests {

    @Autowired
    private RegistrationService registrationService;

    @MockBean
    private UsersRepository usersRepository;

    @MockBean
    private RolesRepository rolesRepository;

    private final static User user = new User(1L, "username", "email", "password", ProviderTypes.LOCAL, null, true);
    private final static UserDto userDto = new UserDto(1L, "username", "email", ProviderTypes.LOCAL, null, true);
    private final static Role role = new Role(1L, "user");
    private final static RoleDto roleDto = new RoleDto(1L, "user");

    @BeforeAll
    public static void init() {
        user.setRoles(Collections.singletonList(role));
        userDto.setRoles(Collections.singletonList(roleDto));
    }

    @Test
    public void registerUserTest() {
        RegisterFormDto registerFormDto = new RegisterFormDto(
                "username",
                "email",
                "password",
                "password"
        );

        Mockito.when(usersRepository.existsByUsernameOrEmail("username", "email")).thenReturn(false);
        Mockito.when(rolesRepository.getByName(UserRoles.USER)).thenReturn(role);
        Mockito.when(usersRepository.save(Mockito.any(User.class))).thenReturn(user);

        UserDto result = registrationService.registerUser(registerFormDto);
        Assertions.assertEquals(user.getUsername(), result.getUsername());
        Assertions.assertEquals(user.getEmail(), result.getEmail());
        Assertions.assertEquals(user.getEnabled(), result.getEnabled());
        Assertions.assertEquals(user.getProvider(), result.getProvider());
        Assertions.assertEquals(1, result.getRoles().size());
    }

    @Test
    public void registerAlreadyExistedUserTest() {
        RegisterFormDto registerFormDto = new RegisterFormDto(
                "username",
                "email",
                "password",
                "password"
        );

        Mockito.when(usersRepository.existsByUsernameOrEmail(Mockito.anyString(), Mockito.anyString())).thenReturn(true);

        Assertions.assertThrows(UserAlreadyExistException.class, () -> registrationService.registerUser(registerFormDto));
    }


    @Test
    public void registerUserWithDifferentPasswordsTest() {
        RegisterFormDto registerFormDto = new RegisterFormDto(
                "username",
                "email",
                "password",
                "wrongPassword"
        );

        Mockito.when(usersRepository.existsByUsernameOrEmail("username", "email")).thenReturn(false);
        Mockito.when(rolesRepository.getByName(UserRoles.USER)).thenReturn(role);

        Assertions.assertThrows(PasswordsMatchException.class, () -> registrationService.registerUser(registerFormDto));
    }

    @Test
    public void registerUserWithServiceExceptionTest() {
        RegisterFormDto registerFormDto = new RegisterFormDto(
                "username",
                "email",
                "password",
                "password"
        );

        Mockito.when(usersRepository.existsByUsernameOrEmail("username", "email")).thenReturn(false);
        Mockito.when(rolesRepository.getByName(UserRoles.USER)).thenReturn(role);
        Mockito.when(usersRepository.save(Mockito.any(User.class))).thenThrow(RuntimeException.class);

        Assertions.assertThrows(UserCreationException.class, () -> registrationService.registerUser(registerFormDto));
    }

    @Test
    public void registerUserWithNullRegistrationFormDataTest() {
        RegisterFormDto registerFormDto = new RegisterFormDto(
                null,
                null,
                null,
                null
        );

        Mockito.when(usersRepository.existsByUsernameOrEmail("username", "email")).thenReturn(false);
        Mockito.when(rolesRepository.getByName(UserRoles.USER)).thenReturn(role);
        Mockito.when(usersRepository.save(Mockito.any(User.class))).thenThrow(RuntimeException.class);

        Assertions.assertThrows(NullPointerException.class, () -> registrationService.registerUser(registerFormDto));
    }

    @Test
    public void registerUserWithNullRegistrationFormTest() {
        Mockito.when(usersRepository.existsByUsernameOrEmail("username", "email")).thenReturn(false);
        Mockito.when(rolesRepository.getByName(UserRoles.USER)).thenReturn(role);
        Mockito.when(usersRepository.save(Mockito.any(User.class))).thenReturn(user);

        Assertions.assertThrows(NullPointerException.class, () -> registrationService.registerUser(null));
    }

}
