package com.cinema.afisha.microservice.controllers.configs;

import com.cinema.afisha.microservice.controllers.security.WebSecurityConfig;
import com.cinema.afisha.microservice.repositories.MovieSeanceSitsRepository;
import com.cinema.afisha.microservice.repositories.MovieSeancesRepository;
import com.cinema.afisha.microservice.repositories.ReservationsRepository;
import com.cinema.afisha.microservice.services.MovieSeancesService;
import com.cinema.afisha.microservice.services.ReservationsService;
import com.cinema.afisha.microservice.services.imliments.MovieSeancesServiceImpl;
import com.cinema.afisha.microservice.services.imliments.ReservationsServiceImpl;
import com.cinema.cinemas.microservice.clients.CinemasApiClient;
import com.cinema.films.microservice.clients.FilmsApiClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

@TestConfiguration
public class AfishaControllersTestConfiguration {

    @Bean
    public MovieSeancesService movieSeancesService() {
        return new MovieSeancesServiceImpl();
    }

    @Bean
    public ReservationsService reservationsService() {
        return new ReservationsServiceImpl();
    }

    @MockBean
    private WebSecurityConfig webSecurityConfig;

    @MockBean
    private MovieSeancesRepository movieSeancesRepository;

    @MockBean
    private MovieSeanceSitsRepository movieSeanceSitsRepository;

    @MockBean
    private CinemasApiClient cinemasApiClient;

    @MockBean
    private FilmsApiClient filmsApiClient;

    @MockBean
    private ReservationsRepository reservationsRepository;

}
