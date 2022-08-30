package com.cinema.afisha.microservice.services.configs;

import com.cinema.afisha.microservice.services.ReservationsService;
import com.cinema.afisha.microservice.services.imliments.ReservationsServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ReservationsServiceTestsConfiguration {

    @Bean
    public ReservationsService reservationsService() {
        return new ReservationsServiceImpl();
    }

}
