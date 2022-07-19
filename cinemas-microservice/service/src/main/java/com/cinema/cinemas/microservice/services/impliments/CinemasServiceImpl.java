package com.cinema.cinemas.microservice.services.impliments;

import com.cinema.cinemas.microservice.domains.CinemaDto;
import com.cinema.cinemas.microservice.domains.HallDto;
import com.cinema.cinemas.microservice.exceptions.services.cinemas.*;
import com.cinema.cinemas.microservice.exceptions.services.halls.HallCreationException;
import com.cinema.cinemas.microservice.models.Cinema;
import com.cinema.cinemas.microservice.models.Hall;
import com.cinema.cinemas.microservice.repositories.CinemasRepository;
import com.cinema.cinemas.microservice.repositories.HallsRepository;
import com.cinema.cinemas.microservice.services.CinemasService;
import com.cinema.cinemas.microservice.services.HallsService;
import com.cinema.cinemas.microservice.services.mappers.CinemaMapper;
import com.cinema.cinemas.microservice.services.mappers.HallMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class CinemasServiceImpl implements CinemasService {

    @Autowired
    private CinemasRepository cinemasRepository;

    @Autowired
    private HallsService hallsService;

    @Autowired
    private HallsRepository hallsRepository;

    @Override
    public List<CinemaDto> getAllCinemas() {
        List<Cinema> cinemas = cinemasRepository.findAll();
        log.info("'{}' cinemas has been found ", cinemas.size());
        return cinemas.stream().map(CinemaMapper.INSTANCE::mapToDto).collect(Collectors.toList());
    }

    @Override
    public CinemaDto getCinema(Long id) {
        if (cinemasRepository.existsById(id)) {
            log.info("Cinema with id '{}' has been found.", id);
            return CinemaMapper.INSTANCE.mapToDto(cinemasRepository.getReferenceById(id));
        } else {
            log.warn("Can't find cinema with id '{}'!", id);
            throw new CinemaNotFoundException(String.format("Can't find cinema with id '%d'!", id));
        }
    }

    @Override
    public CinemaDto createCinema(Cinema cinema) {
        if (!cinemasRepository.existsByName(cinema.getName())) {
            try {
                Cinema createdCinema = cinemasRepository.save(cinema);
                log.info("New cinema with id '{}' has been created.", createdCinema.getId());
                return CinemaMapper.INSTANCE.mapToDto(createdCinema);
            } catch (Exception e) {
                log.error("Error was occurred while creating a cinema! " + e.getMessage());
                throw new CinemaCreationException("Error was occurred while creating a cinema! " + e.getMessage());
            }
        } else {
            log.warn("Can't create a new cinema! Cinema with name '{}' already exists!", cinema.getName());
            throw new CinemaAlreadyExistException(
                    String.format("Can't create a new cinema! Cinema with name '%s' already exists!", cinema.getName())
            );
        }
    }

    @Override
    public CinemaDto updateCinema(Cinema cinema) {
        if (cinemasRepository.existsById(cinema.getId())) {
            try {
                Cinema updatedCinema = cinemasRepository.save(cinema);
                log.info("Cinema with id '{}' has been updated.", updatedCinema.getId());
                return CinemaMapper.INSTANCE.mapToDto(updatedCinema);
            } catch (Exception e) {
                log.error("Error was occurred while updating a cinema! " + e.getMessage());
                throw new CinemaUpdatingException("Error was occurred while updating a cinema! " + e.getMessage());
            }
        } else {
            log.warn("Can't update a cinema! Cinema with id '{}' not exists!", cinema.getId());
            throw new CinemaNotFoundException(
                    String.format("Can't update a cinema! Cinema with id '%d' not exists!", cinema.getId())
            );
        }
    }

    @Override
    public List<Long> deleteCinemasByIds(List<Long> ids) {
        List<Long> deletedIds = new ArrayList<>();
        try {
            ids.forEach(id -> {
                if (cinemasRepository.existsById(id)) {
                    cinemasRepository.deleteById(id);
                    deletedIds.add(id);
                    log.info("Cinema with id '{}' has been deleted.", id);
                } else {
                    log.warn("Can't delete cinema! Cinema with id '{}' not exists!", id);
                }
            });
        } catch (Exception e) {
            log.error("Error was occurred while deleting cinemas! " + e.getMessage());
            throw new CinemaDeletingException("Error was occurred while deleting cinemas! " + e.getMessage());
        }
        return deletedIds;
    }

    @Override
    public HallDto addHall(Long cinemaId, Long sitTypeId, Hall hall) {
        if (cinemasRepository.existsById(cinemaId)) {
            HallDto createdHallDto = hallsService.createHall(sitTypeId, hall);
            try {
                Cinema cinema = cinemasRepository.getReferenceById(cinemaId);
                Hall createdHall = hallsRepository.getReferenceById(createdHallDto.getId());
                cinema.getHalls().add(createdHall);
                log.info("New hall with id '{}' has been added to the cinema with id '{}'.", createdHall.getId(), cinemaId);
                return HallMapper.INSTANCE.mapToDto(createdHall);
            } catch (Exception e) {
                log.error("Error was occurred while adding a new hall to the cinema! " + e.getMessage());
                throw new HallCreationException("Error was occurred adding a new hall to the cinema! " + e.getMessage());
            }
        } else {
            log.warn("Can't add a new hall to cinema! Cinema with id '{}' not exists!", cinemaId);
            throw new CinemaNotFoundException(
                    String.format("Can't add a new hall to cinema! Cinema with id '%d' not exists!", cinemaId)
            );
        }
    }

}
