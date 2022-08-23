package com.cinema.films.microservice.services.configs;

import com.cinema.films.microservice.services.FilmsService;
import com.cinema.films.microservice.services.impliments.FilmsServiceImpl;
import com.cinema.films.microservice.storages.ResourcesStorage;
import com.cinema.films.microservice.storages.implimentations.ResourcesStorageImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class FilmsServicesTestConfiguration {

    @Bean
    public FilmsService filmsService() {
        return new FilmsServiceImpl();
    }

    @Bean
    public ResourcesStorage resourcesStorage() {
        return new ResourcesStorageImpl();
    }

}
