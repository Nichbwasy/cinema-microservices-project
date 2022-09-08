package com.cinema.authorization.microservice.controllers;

import com.cinema.authorization.microservice.controllers.dto.JwtRefreshRequest;
import com.cinema.authorization.microservice.controllers.dto.JwtTokensResponse;
import com.cinema.authorization.microservice.controllers.dto.LogInFormDto;
import com.cinema.authorization.microservice.controllers.security.services.JwtAuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/login")
public class LogInController {

    @Autowired
    private JwtAuthenticationService jwtAuthenticationService;

    @GetMapping("/test/all")
    public ResponseEntity<String> testAllLoginPage() {
        return ResponseEntity.ok().body("Hello! This is the test page to check authorization work for users without any role.");
    }

    @GetMapping("/test/user")
    public ResponseEntity<String> testUserLoginPage() {
        return ResponseEntity.ok().body("Hello! This is the test page to check authorization work for users with role 'USER'.");
    }

    @GetMapping("/test/admin")
    public ResponseEntity<String> testAdminLoginPage() {
        return ResponseEntity.ok().body("Hello! This is the test page to check authorization work for users with role 'ADMIN'.");
    }

    @PostMapping
    public ResponseEntity<JwtTokensResponse> generateToken(@ModelAttribute LogInFormDto form) throws AuthenticationException, AuthException {
        log.info("Trying to log in...");
        JwtTokensResponse response = jwtAuthenticationService.logInUser(form);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/access")
    public ResponseEntity<JwtTokensResponse> accessToken(@ModelAttribute JwtRefreshRequest refreshRequest) {
        log.info("Generating a new access token...");
        JwtTokensResponse response = jwtAuthenticationService.getAccessToken(refreshRequest.getRefreshToken());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtTokensResponse> refreshToken(@ModelAttribute JwtRefreshRequest refreshRequest) {
        log.info("Generating a new access token...");
        JwtTokensResponse response = jwtAuthenticationService.refreshTokens(refreshRequest.getRefreshToken());
        return ResponseEntity.ok().body(response);
    }

}
