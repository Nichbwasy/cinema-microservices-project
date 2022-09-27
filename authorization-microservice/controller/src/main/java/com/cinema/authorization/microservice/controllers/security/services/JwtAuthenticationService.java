package com.cinema.authorization.microservice.controllers.security.services;

import com.cinema.authorization.microservice.controllers.dto.JwtTokensResponse;
import com.cinema.authorization.microservice.controllers.dto.LogInFormDto;
import com.cinema.authorization.microservice.controllers.security.exceptions.JwtTokenValidationException;
import com.cinema.authorization.microservice.controllers.security.jwt.JwtTokenProvider;
import com.cinema.authorization.microservice.domain.UserDto;
import com.cinema.authorization.microservice.exceptions.services.users.UserNotFoundException;
import com.cinema.authorization.microservice.models.User;
import com.cinema.authorization.microservice.services.UsersService;
import com.cinema.authorization.microservice.services.dto.oauth2.GoogleOAuth2User;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;
import javax.validation.constraints.NotNull;

@Slf4j
@Service
public class JwtAuthenticationService {

    @Autowired
    private UsersService usersService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public JwtTokensResponse logInUser(@NonNull LogInFormDto logInForm) throws AuthException {
        UserDto user = usersService.getUserByUsername(logInForm.getUsername());
        if (usersService.checkUserPassword(logInForm.getUsername(), logInForm.getPassword())) {
            String accessToken = jwtTokenProvider.generateAccessJwtToken(user);
            String refreshToken = jwtTokenProvider.generateRefreshJwtToken(user);
            return new JwtTokensResponse(accessToken, refreshToken);
        } else {
            log.warn("Wrong password!");
            throw new AuthException("Wrong password!");
        }
    }

    public JwtTokensResponse logInAuthenticatedUser(@NonNull UserDto userDto) throws AuthException {
        if (usersService.existByEmail(userDto.getEmail())) {
            UserDto user = usersService.getUserByEmail(userDto.getEmail());
            String accessToken = jwtTokenProvider.generateAccessJwtToken(user);
            String refreshToken = jwtTokenProvider.generateRefreshJwtToken(user);
            return new JwtTokensResponse(accessToken, refreshToken);
        } else {
            log.warn("Wrong password!");
            throw new AuthException("Wrong password!");
        }
    }

    public JwtTokensResponse getAccessToken(@NotNull String refreshToken) {
        if (jwtTokenProvider.validateRefreshToken(refreshToken)) {
            Claims claims = jwtTokenProvider.getRefreshClaims(refreshToken);
            String username = claims.getSubject();
            if (usersService.existByUsername(username)) {
                UserDto user = usersService.getUserByUsername(username);
                String accessToken = jwtTokenProvider.generateAccessJwtToken(user);
                return new JwtTokensResponse(accessToken, null);
            } else {
                log.warn("User with username '{}' not found!", username);
                throw new UserNotFoundException(String.format("User with username '%s' not found!", username));
            }
        } else {
            log.warn("Can't get access token! Refresh token is invalid!");
            throw new JwtTokenValidationException("Can't get access token! Refresh token is invalid!");
        }
    }

    public JwtTokensResponse refreshTokens(@NotNull String refreshToken) {
        if (jwtTokenProvider.validateRefreshToken(refreshToken)) {
            Claims claims = jwtTokenProvider.getRefreshClaims(refreshToken);
            String username = claims.getSubject();
            if (usersService.existByUsername(username)) {
                UserDto user = usersService.getUserByUsername(username);
                String accessToken = jwtTokenProvider.generateAccessJwtToken(user);
                String newRefreshToken = jwtTokenProvider.generateRefreshJwtToken(user);
                return new JwtTokensResponse(accessToken, newRefreshToken);
            } else {
                log.warn("User with username '{}' not found!", username);
                throw new UserNotFoundException(String.format("User with username '%s' not found!", username));
            }
        } else {
            log.warn("Can't get access token! Refresh token is invalid!");
            throw new JwtTokenValidationException("Can't get access token! Refresh token is invalid!");
        }
    }

}
