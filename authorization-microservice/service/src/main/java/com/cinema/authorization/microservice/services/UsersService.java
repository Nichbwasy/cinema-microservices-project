package com.cinema.authorization.microservice.services;

import com.cinema.authorization.microservice.domain.UserDto;
import com.cinema.authorization.microservice.services.dto.oauth2.GoogleOAuth2User;

public interface UsersService {
    UserDto getUserByUsername(String username);
    UserDto getUserByEmail(String email);
    Boolean checkUserPassword(String username, String password);
    Boolean existByUsername(String username);
    Boolean existByEmail(String email);
    UserDto processGoogleOAuthPostLogin(GoogleOAuth2User googleOAuth2User);
}
