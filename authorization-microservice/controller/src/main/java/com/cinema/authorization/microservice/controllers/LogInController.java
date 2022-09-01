package com.cinema.authorization.microservice.controllers;

import com.cinema.authorization.microservice.controllers.dto.LogInFormDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/login")
public class LogInController {

    @GetMapping
    public ResponseEntity<?> logInUser(@Valid @ModelAttribute LogInFormDto form) {
        return ResponseEntity.ok().body(null);
    }

}
