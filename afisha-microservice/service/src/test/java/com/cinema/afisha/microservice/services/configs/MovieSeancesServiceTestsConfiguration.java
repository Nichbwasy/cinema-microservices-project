package com.cinema.afisha.microservice.services.configs;

import com.cinema.afisha.microservice.services.MovieSeancesService;
import com.cinema.afisha.microservice.services.imliments.MovieSeancesServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MovieSeancesServiceTestsConfiguration {

    @Bean
    public MovieSeancesService movieSeancesService() {
        return new MovieSeancesServiceImpl();
    }

}
