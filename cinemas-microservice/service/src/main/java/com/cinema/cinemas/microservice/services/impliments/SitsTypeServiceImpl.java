package com.cinema.cinemas.microservice.services.impliments;

import com.cinema.cinemas.microservice.domains.SitTypeDto;
import com.cinema.cinemas.microservice.models.SitType;
import com.cinema.cinemas.microservice.exceptions.services.sits.types.*;
import com.cinema.cinemas.microservice.repositories.SitsTypesRepository;
import com.cinema.cinemas.microservice.services.SitsTypesService;
import com.cinema.cinemas.microservice.services.mappers.SitTypeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class SitsTypeServiceImpl implements SitsTypesService {

    @Autowired
    private SitsTypesRepository sitsTypesRepository;

    @Override
    public List<SitTypeDto> getAllSitsTypes() {
        List<SitType> sitTypes = sitsTypesRepository.findAll();
        log.info("{} sit types has been found.", sitTypes.size());
        return sitTypes.stream().map(SitTypeMapper.INSTANCE::mapToDto).collect(Collectors.toList());
    }

    @Override
    public SitTypeDto getSitType(Long id) {
        if (sitsTypesRepository.existsById(id)) {
            SitType sitType = sitsTypesRepository.getReferenceById(id);
            log.info("Sit type with id '{}' has been found.", id);
            return SitTypeMapper.INSTANCE.mapToDto(sitType);
        } else {
            log.warn("Can't find sit type with id '{}'", id);
            throw new SitTypeNotFoundException(String.format("Can't find sit type with id '%d'", id));
        }
    }

    @Override
    public SitTypeDto createSitType(SitType sit) {
        if (!sitsTypesRepository.existsByName(sit.getName())) {
            try {
                SitType createdSitType = sitsTypesRepository.save(sit);
                log.info("New sit type with id '{}' has been created.", createdSitType.getId());
                return SitTypeMapper.INSTANCE.mapToDto(createdSitType);
            } catch (Exception e) {
                log.error("Error was occurred while creating new sit type! " + e.getMessage());
                throw new SitTypeCreationException("Error was occurred while creating new sit type! " + e.getMessage());
            }
        } else {
            log.warn("Can't create sit type! Sit type with name '{}' already exists!", sit.getName());
            throw new SitTypeAlreadyExistException(
                    String.format("Can't create sit type! Sit type with name '%s' already exists!", sit.getName()));
        }
    }

    @Override
    public SitTypeDto updateSitType(SitType sit) {
        if (sitsTypesRepository.existsById(sit.getId())) {
            try {
                SitType updatedSitType = sitsTypesRepository.save(sit);
                log.info("Sit type with id '{}' has been updated.", updatedSitType.getId());
                return SitTypeMapper.INSTANCE.mapToDto(updatedSitType);
            } catch (Exception e) {
                log.error("Error was occurred while updating sit type! " + e.getMessage());
                throw new SitTypeUpdatingException("Error was occurred while updating sit type! " + e.getMessage());
            }
        } else {
            log.warn("Can't update sit type! Sit type with id '{}' not exists!", sit.getId());
            throw new SitTypeNotFoundException(
                    String.format("Can't update sit type! Sit type with id '%d' not exists!", sit.getId()));
        }
    }

    @Override
    public Long deleteSitType(Long id) {
        if (sitsTypesRepository.existsById(id)) {
            try {
                sitsTypesRepository.deleteById(id);
                log.info("Sit type with id '{}' was deleted.", id);
                return id;
            } catch (Exception e) {
                log.error("Error was occurred while deleting sit type! " + e.getMessage());
                throw new SitTypeDeletingException("Error was occurred while deleting sit type! " + e.getMessage());
            }
        } else {
            log.warn("Can't delete sit type! Sit type with id '{}' not exists!", id);
            throw new SitTypeAlreadyExistException(
                    String.format("Can't delete sit type! Sit type with id '%d' not exists!", id));
        }
    }
}
