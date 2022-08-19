package com.cinema.cinemas.microservice.services.configs;

import com.cinema.cinemas.microservice.services.CinemasService;
import com.cinema.cinemas.microservice.services.impliments.CinemasServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class CinemasServiceTestsConfiguration {
    @Bean
    public CinemasService cinemasService() {
        return new CinemasServiceImpl();
    }
}
