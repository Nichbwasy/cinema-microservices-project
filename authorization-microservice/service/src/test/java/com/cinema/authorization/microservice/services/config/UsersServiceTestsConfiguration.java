package com.cinema.authorization.microservice.services.config;

import com.cinema.authorization.microservice.services.UsersService;
import com.cinema.authorization.microservice.services.implimets.UsersServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class UsersServiceTestsConfiguration {

    @Bean
    public UsersService usersService() {
        return new UsersServiceImpl();
    }

}
