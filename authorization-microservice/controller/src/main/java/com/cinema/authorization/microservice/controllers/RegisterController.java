package com.cinema.authorization.microservice.controllers;

import com.cinema.authorization.microservice.services.dto.RegisterFormDto;
import com.cinema.authorization.microservice.domain.UserDto;
import com.cinema.authorization.microservice.services.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private RegistrationService usersService;

    @PostMapping
    public ResponseEntity<UserDto> registerUser(@Valid @ModelAttribute RegisterFormDto form) {
        UserDto userDto = usersService.registerUser(form);
        log.info("New user has been registered!");
        return ResponseEntity.ok().body(userDto);
    }

}
