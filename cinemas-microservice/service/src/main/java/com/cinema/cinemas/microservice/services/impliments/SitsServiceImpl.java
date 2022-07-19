package com.cinema.cinemas.microservice.services.impliments;

import com.cinema.cinemas.microservice.domains.SitDto;
import com.cinema.cinemas.microservice.exceptions.services.sits.SitCreationException;
import com.cinema.cinemas.microservice.exceptions.services.sits.SitDeletingException;
import com.cinema.cinemas.microservice.exceptions.services.sits.SitNotFoundException;
import com.cinema.cinemas.microservice.exceptions.services.sits.SitUpdatingException;
import com.cinema.cinemas.microservice.models.Sit;
import com.cinema.cinemas.microservice.repositories.SitsRepository;
import com.cinema.cinemas.microservice.services.SitsService;
import com.cinema.cinemas.microservice.services.mappers.SitMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
public class SitsServiceImpl implements SitsService {

    @Autowired
    private SitsRepository sitsRepository;

    @Override
    public SitDto getSit(Long id) {
        if (sitsRepository.existsById(id)) {
            Sit sit = sitsRepository.getReferenceById(id);
            log.info("Sit with id '{}' has been found.", id);
            return SitMapper.INSTANCE.mapToDto(sit);
        } else {
            log.warn("Can't find sit with id '{}'!", id);
            throw new SitNotFoundException(String.format("Can't find sit with id '%d'!", id));
        }
    }

    @Override
    public SitDto createSit(Sit sit) {
        try {
            Sit createdSit = sitsRepository.save(sit);
            log.info("New sit with id '{}' has been created.", createdSit.getId());
            return SitMapper.INSTANCE.mapToDto(createdSit);
        } catch (Exception e) {
            log.error("Error was occurred while creating new sit! " + e.getMessage());
            throw new SitCreationException("Error was occurred while creating new sit! " + e.getMessage());
        }
    }

    @Override
    public SitDto updateSit(Sit sit) {
        if (sitsRepository.existsById(sit.getId())) {
            try {
                Sit updatedSit = sitsRepository.save(sit);
                log.info("Sit with if '{}' has been updated.", updatedSit.getId());
                return SitMapper.INSTANCE.mapToDto(updatedSit);
            } catch (Exception e) {
                log.error("Error was occurred while updating sit! " + e.getMessage());
                throw new SitUpdatingException("Error was occurred while updating sit! " + e.getMessage());
            }
        } else {
            log.warn("Can't update sit! Can't find sit with id '{}'!", sit.getId());
            throw new SitNotFoundException(String.format("Can't update sit! Can't find sit with id '%d'!", sit.getId()));
        }
    }

    @Override
    public Long deleteSit(Long id) {
        if (sitsRepository.existsById(id)) {
            try {
                sitsRepository.deleteById(id);
                log.info("Sit with id '{}' has been deleted!", id);
                return id;
            } catch (Exception e) {
                log.error("Error was occurred while deleting sit! " + e.getMessage());
                throw new SitDeletingException("Error was occurred while deleting sit! " + e.getMessage());
            }
        } else {
            log.warn("Can't delete sit! Sit with id '{}' not found!", id);
            throw new SitNotFoundException(String.format("Can't delete sit! Sit with id '%d' not found!", id));
        }
    }
}
