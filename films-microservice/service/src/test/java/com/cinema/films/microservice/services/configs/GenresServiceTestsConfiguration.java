package com.cinema.films.microservice.services.configs;

import com.cinema.films.microservice.services.GenresService;
import com.cinema.films.microservice.services.impliments.GenresServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class GenresServiceTestsConfiguration {

    @Bean
    public GenresService genresService() {
        return new GenresServiceImpl();
    }

}
