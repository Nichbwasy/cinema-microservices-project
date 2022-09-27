package com.cinema.authorization.microservice.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/login/oauth2")
public class OAuth2Controller {

    @GetMapping
    public ResponseEntity<String> oAuth2LoginPage() {
        return ResponseEntity.ok().body("Hello this OAUTH2 login page!");
    }

}
