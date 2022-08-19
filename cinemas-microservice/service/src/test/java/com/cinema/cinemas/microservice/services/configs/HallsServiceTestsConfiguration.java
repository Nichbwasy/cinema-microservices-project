package com.cinema.cinemas.microservice.services.configs;

import com.cinema.cinemas.microservice.services.HallsService;
import com.cinema.cinemas.microservice.services.impliments.HallsServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class HallsServiceTestsConfiguration {
    @Bean
    public HallsService hallsService() {
        return new HallsServiceImpl();
    }
}
