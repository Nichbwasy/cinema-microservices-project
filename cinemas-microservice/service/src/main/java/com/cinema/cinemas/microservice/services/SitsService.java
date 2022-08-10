package com.cinema.cinemas.microservice.services;

import com.cinema.cinemas.microservice.domains.SitDto;
import com.cinema.cinemas.microservice.exceptions.services.sits.SitCreationException;
import com.cinema.cinemas.microservice.exceptions.services.sits.SitDeletingException;
import com.cinema.cinemas.microservice.exceptions.services.sits.SitNotFoundException;
import com.cinema.cinemas.microservice.exceptions.services.sits.SitUpdatingException;
import com.cinema.cinemas.microservice.models.Sit;

public interface SitsService {

    /**
     * Returns sit by id from repository.
     * @param id Id of sit.
     * @return DTO of sit.
     * @throws SitNotFoundException Throws when sit with selected id not found in repository.
     */
    SitDto getSit(Long id) throws SitNotFoundException;

    /**
     * Creayes a new sit in repository.
     * @param sit Sit data.
     * @return DTO of created sit.
     * @throws SitCreationException Throws if any exception occurred while saving a new sit.
     */
    SitDto createSit(Sit sit) throws SitCreationException;

    /**
     * Updates a sit data in repository.
     * @param sit Updated sit data.
     * @return DTO of updated sit.
     * @throws SitNotFoundException  Throws when sit with selected id not found in repository.
     * @throws SitUpdatingException Throws if any exception occurred while updating a sit.
     */
    SitDto updateSit(Sit sit) throws SitNotFoundException, SitUpdatingException;

    /**
     * Removes a sit from repository.
     * @param id Id of sit.
     * @return Id of deleted sit.
     * @throws SitNotFoundException  Throws when sit with selected id not found in repository.
     * @throws SitDeletingException Throws if any exception occurred while deleting a sit.
     */
    Long deleteSit(Long id) throws SitNotFoundException, SitDeletingException;
}
