package com.cinema.authorization.microservice.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterFormDto {

    @NotNull(message = "Username is mandatory!")
    @Size(min = 3, max = 128, message = "Username size must be in between 3 and 128 characters!")
    @Pattern(regexp = "^[0-9a-zA-Z._\\-]{3,128}+$", message = "Invalid username!")
    private String username;

    @NotNull(message = "Email is mandatory!")
    @Size(min = 3, max = 128, message = "Email size must be in between 3 and 128 characters!")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-]+$", message = "Invalid email!")
    private String email;

    @NotNull(message = "Password is mandatory!")
    @Size(min = 4, max = 64, message = "Password size must be in between 4 and 64 characters!")
    @Pattern(regexp = "^[0-9a-zA-Z._#@$^&*\\-]{4,64}+$", message = "Invalid password!")
    private String password;

    @NotNull(message = "Repeat password is mandatory!")
    @Size(min = 4, max = 64, message = "Repeat password size must be in between 4 and 64 characters!")
    @Pattern(regexp = "^[0-9a-zA-Z._#@$^&*\\-]{4,64}+$", message = "Invalid repeat password!")
    private String repeatPassword;

}
