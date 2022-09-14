package com.cinema.films.microservice.controllers.configs;

import com.cinema.films.microservice.controllers.FilmsController;
import com.cinema.films.microservice.controllers.security.WebSecurityConfig;
import com.cinema.films.microservice.repositories.DirectorsRepository;
import com.cinema.films.microservice.repositories.FilmImgResourcesRepository;
import com.cinema.films.microservice.repositories.FilmsRepository;
import com.cinema.films.microservice.repositories.GenresRepository;
import com.cinema.films.microservice.services.DirectorsService;
import com.cinema.films.microservice.services.FilmsService;
import com.cinema.films.microservice.services.GenresService;
import com.cinema.films.microservice.services.impliments.DirectorsServiceImpl;
import com.cinema.films.microservice.services.impliments.FilmsServiceImpl;
import com.cinema.films.microservice.services.impliments.GenresServiceImpl;
import com.cinema.films.microservice.storages.ResourcesStorage;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class FilmsControllersTestConfiguration {

    @Bean
    public GenresService genresService() {
        return new GenresServiceImpl();
    }

    @Bean
    public DirectorsService directorsService() {
        return new DirectorsServiceImpl();
    }

    @Bean
    public FilmsService filmsService() {
        return new FilmsServiceImpl();
    }

    @MockBean
    private WebSecurityConfig webSecurityConfig;

    @MockBean
    private GenresRepository genresRepository;

    @MockBean
    private DirectorsRepository directorsRepository;

    @MockBean
    private FilmsRepository filmsRepository;

    @MockBean
    private ResourcesStorage resourcesStorage;

    @MockBean
    private FilmImgResourcesRepository filmImgResourcesRepository;

}
