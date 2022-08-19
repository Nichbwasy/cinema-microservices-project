package com.cinema.cinemas.microservice.controllers.configs;

import com.cinema.cinemas.microservice.controllers.CinemaController;
import com.cinema.cinemas.microservice.models.Cinema;
import com.cinema.cinemas.microservice.repositories.CinemasRepository;
import com.cinema.cinemas.microservice.repositories.HallsRepository;
import com.cinema.cinemas.microservice.repositories.SitsRepository;
import com.cinema.cinemas.microservice.repositories.SitsTypesRepository;
import com.cinema.cinemas.microservice.services.CinemasService;
import com.cinema.cinemas.microservice.services.HallsService;
import com.cinema.cinemas.microservice.services.SitsService;
import com.cinema.cinemas.microservice.services.SitsTypesService;
import com.cinema.cinemas.microservice.services.impliments.CinemasServiceImpl;
import com.cinema.cinemas.microservice.services.impliments.HallsServiceImpl;
import com.cinema.cinemas.microservice.services.impliments.SitsServiceImpl;
import com.cinema.cinemas.microservice.services.impliments.SitsTypeServiceImpl;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;


public class ControllersTestConfiguration {

    @Bean
    public CinemasService cinemasService() {
        return new CinemasServiceImpl();
    }

    @Bean
    public HallsService hallsService() {
        return new HallsServiceImpl();
    }

    @Bean
    public SitsService sitsService() {
        return new SitsServiceImpl();
    }

    @Bean
    public SitsTypesService sitsTypesService() {
        return new SitsTypeServiceImpl();
    }

    @MockBean
    private CinemasRepository cinemasRepository;

    @MockBean
    private HallsRepository hallsRepository;

    @MockBean
    private SitsRepository sitsRepository;

    @MockBean
    private SitsTypesRepository sitsTypesRepository;

}
