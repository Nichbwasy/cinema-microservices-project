package com.cinema.authorization.microservice.services.config;

import com.cinema.authorization.microservice.services.RegistrationService;
import com.cinema.authorization.microservice.services.implimets.RegistrationServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RegistrationServiceTestsConfiguration {

    @Bean
    public RegistrationService registrationService() {
        return new RegistrationServiceImpl();
    }

}
