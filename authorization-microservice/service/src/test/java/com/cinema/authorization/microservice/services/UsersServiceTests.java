package com.cinema.authorization.microservice.services;

import com.cinema.authorization.microservice.domain.RoleDto;
import com.cinema.authorization.microservice.domain.UserDto;
import com.cinema.authorization.microservice.exceptions.services.users.UserNotFoundException;
import com.cinema.authorization.microservice.models.Role;
import com.cinema.authorization.microservice.models.User;
import com.cinema.authorization.microservice.repositories.RolesRepository;
import com.cinema.authorization.microservice.repositories.UsersRepository;
import com.cinema.authorization.microservice.services.config.UsersServiceTestsConfiguration;
import com.cinema.authorization.microservice.services.dto.oauth2.GoogleOAuth2User;
import com.cinema.common.utils.authorizations.providers.ProviderTypes;
import com.cinema.common.utils.authorizations.roles.UserRoles;
import com.cinema.common.utils.generators.encoders.EncoderGenerator;
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

@SpringBootTest(classes = {UsersService.class})
@Import(UsersServiceTestsConfiguration.class)
@ExtendWith(SpringExtension.class)
public class UsersServiceTests {

    @Autowired
    private UsersService usersService;

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
        user.setPassword(EncoderGenerator.getBCryptPasswordEncoder().encode("password"));
        user.setRoles(Collections.singletonList(role));
        userDto.setRoles(Collections.singletonList(roleDto));
    }

    @Test
    public void getUserByUsernameTest() {
        Mockito.when(usersRepository.existsByUsername("username")).thenReturn(true);
        Mockito.when(usersRepository.getByUsername("username")).thenReturn(user);

        UserDto result = usersService.getUserByUsername("username");
        Assertions.assertEquals(user.getUsername(), result.getUsername());
        Assertions.assertEquals(user.getEmail(), result.getEmail());
        Assertions.assertEquals(user.getEnabled(), result.getEnabled());
        Assertions.assertEquals(user.getProvider(), result.getProvider());
        Assertions.assertEquals(1, result.getRoles().size());
    }

    @Test
    public void getNotExistedUserByUsernameTest() {
        Mockito.when(usersRepository.existsByUsername(Mockito.anyString())).thenReturn(false);

        Assertions.assertThrows(UserNotFoundException.class, () -> usersService.getUserByUsername("username"));
    }

    @Test
    public void getUserByUsernameWithServiceExceptionTest() {
        Mockito.when(usersRepository.existsByUsername(Mockito.anyString())).thenReturn(true);
        Mockito.when(usersRepository.getByUsername(Mockito.anyString())).thenThrow(RuntimeException.class);

        Assertions.assertThrows(RuntimeException.class, () -> usersService.getUserByUsername("username"));
    }

    @Test
    public void getUserByUsernameWithNullUsernameTest() {
        Mockito.when(usersRepository.existsByUsername(Mockito.nullable(String.class))).thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class, () -> usersService.getUserByUsername(null));
    }

    @Test
    public void getUserByEmailTest() {
        Mockito.when(usersRepository.existsByEmail("email")).thenReturn(true);
        Mockito.when(usersRepository.getByEmail("email")).thenReturn(user);

        UserDto result = usersService.getUserByEmail("email");
        Assertions.assertEquals(user.getUsername(), result.getUsername());
        Assertions.assertEquals(user.getEmail(), result.getEmail());
        Assertions.assertEquals(user.getEnabled(), result.getEnabled());
        Assertions.assertEquals(user.getProvider(), result.getProvider());
        Assertions.assertEquals(1, result.getRoles().size());
    }

    @Test
    public void getNotExistedUserByEmailTest() {
        Mockito.when(usersRepository.existsByEmail(Mockito.anyString())).thenReturn(false);

        Assertions.assertThrows(UserNotFoundException.class, () -> usersService.getUserByEmail("email"));
    }

    @Test
    public void getUserByEmailWithRepositoryExceptionTest() {
        Mockito.when(usersRepository.existsByEmail(Mockito.anyString())).thenReturn(true);
        Mockito.when(usersRepository.getByEmail(Mockito.anyString())).thenThrow(RuntimeException.class);

        Assertions.assertThrows(RuntimeException.class, () -> usersService.getUserByEmail("email"));
    }

    @Test
    public void getUserByEmailWithNullUsernameTest() {
        Mockito.when(usersRepository.existsByEmail(Mockito.nullable(String.class))).thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class, () -> usersService.getUserByEmail(null));
    }

    @Test
    public void checkUserPasswordTest() {
        Mockito.when(usersRepository.existsByUsername("username")).thenReturn(true);
        Mockito.when(usersRepository.getByUsername("username")).thenReturn(user);

        Assertions.assertTrue(usersService.checkUserPassword("username", "password"));
    }

    @Test
    public void checkWrongUserPasswordTest() {
        Mockito.when(usersRepository.existsByUsername("username")).thenReturn(true);
        Mockito.when(usersRepository.getByUsername("username")).thenReturn(user);

        Assertions.assertFalse(usersService.checkUserPassword("username", "wrongPassword"));
    }

    @Test
    public void checkUserPasswordForNotExistedUserTest() {
        Mockito.when(usersRepository.existsByUsername(Mockito.anyString())).thenReturn(false);

        Assertions.assertThrows(UserNotFoundException.class, () -> usersService.checkUserPassword("username", "wrongPassword"));
    }

    @Test
    public void checkUserPasswordWithRepositoryExceptionTest() {
        Mockito.when(usersRepository.existsByUsername("username")).thenReturn(true);
        Mockito.when(usersRepository.getByUsername(Mockito.anyString())).thenThrow(RuntimeException.class);

        Assertions.assertThrows(RuntimeException.class, () -> usersService.checkUserPassword("username", "password"));
    }

    @Test
    public void checkUserPasswordWithNullDataTest() {
        Mockito.when(usersRepository.existsByUsername(Mockito.nullable(String.class))).thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class, () -> usersService.checkUserPassword(null, null));
    }

    @Test
    public void processGoogleOAuthPostLoginTest() {
        GoogleOAuth2User mockUser = Mockito.mock(GoogleOAuth2User.class);

        Mockito.when(mockUser.getEmail()).thenReturn("email");
        Mockito.when(usersRepository.existsByEmail("email")).thenReturn(true);
        Mockito.when(usersRepository.getByEmail("email")).thenReturn(user);

        UserDto result = usersService.processGoogleOAuthPostLogin(mockUser);
        Assertions.assertEquals(user.getUsername(), result.getUsername());
        Assertions.assertEquals(user.getEmail(), result.getEmail());
        Assertions.assertEquals(user.getEnabled(), result.getEnabled());
        Assertions.assertEquals(user.getProvider(), result.getProvider());
        Assertions.assertEquals(1, result.getRoles().size());
    }

    @Test
    public void processNotRegisteredUserGoogleOAuthPostLoginTest() {
        GoogleOAuth2User mockUser = Mockito.mock(GoogleOAuth2User.class);

        Mockito.when(mockUser.getEmail()).thenReturn("email");
        Mockito.when(usersRepository.existsByEmail("email")).thenReturn(false);
        Mockito.when(rolesRepository.getByName(UserRoles.USER)).thenReturn(role);
        Mockito.when(usersRepository.save(Mockito.any(User.class))).thenReturn(user);

        UserDto result = usersService.processGoogleOAuthPostLogin(mockUser);
        Assertions.assertEquals(user.getUsername(), result.getUsername());
        Assertions.assertEquals(user.getEmail(), result.getEmail());
        Assertions.assertEquals(user.getEnabled(), result.getEnabled());
        Assertions.assertEquals(user.getProvider(), result.getProvider());
        Assertions.assertEquals(1, result.getRoles().size());
    }

    @Test
    public void processGoogleOAuthPostLoginWithRepositoryExceptionTest() {
        GoogleOAuth2User mockUser = Mockito.mock(GoogleOAuth2User.class);

        Mockito.when(mockUser.getEmail()).thenReturn("email");
        Mockito.when(usersRepository.existsByEmail("email")).thenReturn(true);
        Mockito.when(usersRepository.getByEmail("email")).thenThrow(RuntimeException.class);

        Assertions.assertThrows(RuntimeException.class, () -> usersService.processGoogleOAuthPostLogin(mockUser));
    }

    @Test
    public void processGoogleOAuthPostLoginWithNullUserTest() {
        Mockito.when(usersRepository.existsByEmail(Mockito.nullable(String.class))).thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class, () -> usersService.processGoogleOAuthPostLogin(null));
    }

    @Test
    public void existByUsernameTest() {
        Mockito.when(usersRepository.existsByUsername("username")).thenReturn(true);

        Assertions.assertTrue(usersService.existByUsername("username"));
    }

    @Test
    public void NotExistByUsernameTest() {
        Mockito.when(usersRepository.existsByUsername("username")).thenReturn(false);

        Assertions.assertFalse(usersService.existByUsername("username"));
    }

    @Test
    public void existByUsernameWithRepositoryExceptionTest() {
        Mockito.when(usersRepository.existsByUsername(Mockito.anyString())).thenThrow(RuntimeException.class);

        Assertions.assertThrows(RuntimeException.class, () -> usersService.existByUsername("username"));
    }

    @Test
    public void existByUsernameWithNullDataTest() {
        Mockito.when(usersRepository.existsByUsername(Mockito.nullable(String.class))).thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class, () -> usersService.existByUsername(null));
    }

    @Test
    public void existByEmailTest() {
        Mockito.when(usersRepository.existsByEmail("email")).thenReturn(true);

        Assertions.assertTrue(usersService.existByEmail("email"));
    }

    @Test
    public void NotExistByEmailTest() {
        Mockito.when(usersRepository.existsByEmail("email")).thenReturn(false);

        Assertions.assertFalse(usersService.existByEmail("email"));
    }

    @Test
    public void existByEmailWithRepositoryExceptionTest() {
        Mockito.when(usersRepository.existsByEmail(Mockito.anyString())).thenThrow(RuntimeException.class);

        Assertions.assertThrows(RuntimeException.class, () -> usersService.existByEmail("email"));
    }

    @Test
    public void existByEmailWithNullDataTest() {
        Mockito.when(usersRepository.existsByEmail(Mockito.nullable(String.class))).thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class, () -> usersService.existByEmail(null));
    }

}
