package com.cinema.cinema.project.cinemas.microservice.services;

import com.cinema.cinema.project.cinemas.microservice.domains.SitDto;
import com.cinema.cinema.project.cinemas.microservice.exceptions.services.sits.SitCreationException;
import com.cinema.cinema.project.cinemas.microservice.exceptions.services.sits.SitDeletingException;
import com.cinema.cinema.project.cinemas.microservice.exceptions.services.sits.SitNotFoundException;
import com.cinema.cinema.project.cinemas.microservice.exceptions.services.sits.SitUpdatingException;
import com.cinema.cinema.project.cinemas.microservice.models.Sit;

public interface SitsService {
    SitDto getSit(Long id) throws SitNotFoundException;
    SitDto createSit(Sit sit) throws SitCreationException;
    SitDto updateSit(Sit sit) throws SitNotFoundException, SitUpdatingException;
    Long deleteSit(Long id) throws SitNotFoundException, SitDeletingException;
}
