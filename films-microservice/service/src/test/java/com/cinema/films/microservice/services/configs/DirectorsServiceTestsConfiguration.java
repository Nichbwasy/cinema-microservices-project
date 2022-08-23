package com.cinema.films.microservice.services.configs;

import com.cinema.films.microservice.services.DirectorsService;
import com.cinema.films.microservice.services.impliments.DirectorsServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class DirectorsServiceTestsConfiguration {

    @Bean
    public DirectorsService genresService() {
        return new DirectorsServiceImpl();
    }

}
