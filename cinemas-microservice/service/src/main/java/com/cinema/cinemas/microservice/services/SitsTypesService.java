package com.cinema.cinemas.microservice.services;

import com.cinema.cinemas.microservice.domains.SitTypeDto;
import com.cinema.cinemas.microservice.models.SitType;
import com.cinema.cinemas.microservice.exceptions.services.sits.types.*;

import java.util.List;

public interface SitsTypesService {

    /**
     * Returns all sit types from repository.
     * @return List of all sit types DTO's.
     */
    List<SitTypeDto> getAllSitsTypes();

    /**
     * Returns from repository sit type by id.
     * @param id Id of sit type.
     * @return DTO of sit type
     * @throws SitTypeNotFoundException Throws when sit type with selected id not found in repository.
     */
    SitTypeDto getSitType(Long id) throws SitTypeNotFoundException;

    /**
     * Creates a new sit type in repository.
     * @param sit Sit type data.
     * @return DTO of sit type.
     * @throws SitTypeAlreadyExistException Throws when sit type with selected name already exists in repository.
     * @throws SitTypeCreationException Throws if any exception occurred while saving a new sit type.
     */
    SitTypeDto createSitType(SitType sit) throws SitTypeAlreadyExistException, SitTypeCreationException;

    /**
     * Updates a sit type in repository.
     * @param sit Updated sit type data.
     * @return DTO of updated sit type.
     * @throws SitTypeNotFoundException Throws when sit type with selected id not found in repository.
     * @throws SitTypeUpdatingException Throws if any exception occurred while updating a sit type.
     */
    SitTypeDto updateSitType(SitType sit) throws SitTypeNotFoundException, SitTypeUpdatingException;

    /**
     * Removes a sit type from repository by id.
     * @param id Id of sit type to remove.
     * @return Id of deleted sit type.
     * @throws SitTypeNotFoundException Throws when sit type with selected id not found in repository.
     * @throws SitTypeDeletingException Throws if any exception occurred while deleting a sit type.
     */
    Long deleteSitType(Long id) throws SitTypeNotFoundException, SitTypeDeletingException;
}
