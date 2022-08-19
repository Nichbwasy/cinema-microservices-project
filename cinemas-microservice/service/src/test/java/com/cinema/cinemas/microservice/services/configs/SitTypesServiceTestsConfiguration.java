package com.cinema.cinemas.microservice.services.configs;

import com.cinema.cinemas.microservice.services.SitsTypesService;
import com.cinema.cinemas.microservice.services.impliments.SitsTypeServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class SitTypesServiceTestsConfiguration {

    @Bean
    public SitsTypesService sitsTypesService() {
        return new SitsTypeServiceImpl();
    }

}
