package com.cinema.authorization.microservice.controllers;

import com.cinema.authorization.microservice.controllers.dto.JwtTokensResponse;
import com.cinema.authorization.microservice.controllers.dto.LogInFormDto;
import com.cinema.authorization.microservice.controllers.security.services.JwtAuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;

@Slf4j
@RestController
@RequestMapping("/login")
public class LogInController {

    @Autowired
    private JwtAuthenticationService jwtAuthenticationService;

    @GetMapping
    public ResponseEntity<String> loginPage() {
        return ResponseEntity.ok().body("Hello this is login page!");
    }

    @PostMapping
    public ResponseEntity<JwtTokensResponse> generateToken(@ModelAttribute LogInFormDto form) throws AuthenticationException, AuthException {
        log.info("Trying to log in...");
        JwtTokensResponse response = jwtAuthenticationService.logInUser(form);
        return ResponseEntity.ok().body(response);
    }


}
