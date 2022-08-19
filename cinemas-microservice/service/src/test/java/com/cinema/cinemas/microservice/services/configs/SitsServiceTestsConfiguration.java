package com.cinema.cinemas.microservice.services.configs;

import com.cinema.cinemas.microservice.services.SitsService;
import com.cinema.cinemas.microservice.services.impliments.SitsServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class SitsServiceTestsConfiguration {
    @Bean
    public SitsService sitsService() {
        return new SitsServiceImpl();
    }
}
