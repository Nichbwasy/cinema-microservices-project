package com.cinema.cinema.project.cinemas.microservice.services;

import com.cinema.cinema.project.cinemas.microservice.domains.SitTypeDto;
import com.cinema.cinema.project.cinemas.microservice.exceptions.services.sits.types.*;
import com.cinema.cinema.project.cinemas.microservice.models.SitType;

import java.util.List;

public interface SitsTypesService {
    List<SitTypeDto> getAllSitsTypes();
    SitTypeDto getSitType(Long id) throws SitTypeNotFoundException;
    SitTypeDto createSitType(SitType sit) throws SitTypeAlreadyExistException, SitTypeCreationException;
    SitTypeDto updateSitType(SitType sit) throws SitTypeNotFoundException, SitTypeUpdatingException;
    Long deleteSitType(Long id) throws SitTypeNotFoundException, SitTypeDeletingException;
}
