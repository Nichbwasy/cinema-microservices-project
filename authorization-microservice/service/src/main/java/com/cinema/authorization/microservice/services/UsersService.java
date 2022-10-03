package com.cinema.authorization.microservice.services;

import com.cinema.authorization.microservice.domain.UserDto;
import com.cinema.authorization.microservice.exceptions.services.users.UserNotFoundException;
import com.cinema.authorization.microservice.services.dto.oauth2.GoogleOAuth2User;

public interface UsersService {

    /**
     * Gents user from database and returns DTO.
     * @param username User username.
     * @return DTO with user data.
     * @throws UserNotFoundException Throws when user with selected username not found in database.
     */
    UserDto getUserByUsername(String username) throws UserNotFoundException;

    /**
     * Gets user from database and returns DTO.
     * @param email User email.
     * @return DTO with user data.
     * @throws UserNotFoundException Throws when user with selected email not found in database.
     */
    UserDto getUserByEmail(String email) throws UserNotFoundException;

    /**
     * Validate password for user with selected username.
     * @param username Username of user.
     * @param password Password of user to check.
     * @return Returns true if password match, otherwise return false.
     * @throws UserNotFoundException Throws when user with selected username not found in database.
     */
    Boolean checkUserPassword(String username, String password) throws UserNotFoundException;

    /**
     * Check is user with selected username exists in database.
     * @param username Username to check.
     * @return Returns true if user with selected username exist in database, otherwise return false.
     */
    Boolean existByUsername(String username);

    /**
     * Check is user with selected email exists in database.
     * @param email Email to check.
     * @return Returns true if user with selected email exist in database, otherwise return false.
     */
    Boolean existByEmail(String email);

    /**
     * Register a new user or gets existed if client authenticated via google.
     * @param googleOAuth2User Google oAuth2 principal to authenticate.
     * @return Returns user DTO which was found or created in database.
     */
    UserDto processGoogleOAuthPostLogin(GoogleOAuth2User googleOAuth2User);
}
