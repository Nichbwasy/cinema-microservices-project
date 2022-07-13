package com.cinema.cinema.project.cinemas.microservice.services.impliments;

import com.cinema.cinema.project.cinemas.microservice.domains.HallDto;
import com.cinema.cinema.project.cinemas.microservice.domains.SitDto;
import com.cinema.cinema.project.cinemas.microservice.exceptions.services.cinemas.CinemaNotFoundException;
import com.cinema.cinema.project.cinemas.microservice.exceptions.services.halls.HallCreationException;
import com.cinema.cinema.project.cinemas.microservice.exceptions.services.halls.HallDeletingException;
import com.cinema.cinema.project.cinemas.microservice.exceptions.services.halls.HallNotFoundException;
import com.cinema.cinema.project.cinemas.microservice.exceptions.services.halls.HallUpdatingException;
import com.cinema.cinema.project.cinemas.microservice.models.Cinema;
import com.cinema.cinema.project.cinemas.microservice.models.Hall;
import com.cinema.cinema.project.cinemas.microservice.models.Sit;
import com.cinema.cinema.project.cinemas.microservice.models.SitType;
import com.cinema.cinema.project.cinemas.microservice.repositories.CinemasRepository;
import com.cinema.cinema.project.cinemas.microservice.repositories.HallsRepository;
import com.cinema.cinema.project.cinemas.microservice.repositories.SitsRepository;
import com.cinema.cinema.project.cinemas.microservice.repositories.SitsTypesRepository;
import com.cinema.cinema.project.cinemas.microservice.services.HallsService;
import com.cinema.cinema.project.cinemas.microservice.services.mappers.HallMapper;
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
public class HallsServiceImpl implements HallsService {

    @Autowired
    private HallsRepository hallsRepository;

    @Autowired
    private CinemasRepository cinemasRepository;

    @Autowired
    private SitsRepository sitsRepository;

    @Autowired
    private SitsTypesRepository sitsTypesRepository;

    @Override
    public List<HallDto> getAllHallsInCinema(Long cinemaId) {
        if (cinemasRepository.existsById(cinemaId)) {
            Cinema cinema = cinemasRepository.getReferenceById(cinemaId);
            log.info("Halls of the cinema with id '{}' was found.", cinemaId);
            return cinema.getHalls().stream().map(HallMapper.INSTANCE::mapToDto).collect(Collectors.toList());
        } else {
            log.warn("Cinema with id '{}' not found!", cinemaId);
            throw new CinemaNotFoundException(String.format("Cinema with id '%d' not found!", cinemaId));
        }
    }

    @Override
    public HallDto getCinemaHall(Long cinemaId, Long hallId) {
        if (cinemasRepository.existsById(cinemaId)) {
            Cinema cinema = cinemasRepository.getReferenceById(cinemaId);
            if (cinema.getHalls().stream().anyMatch(hall -> hall.getId().equals(hallId))) {
                log.info("Hall with id '{}' has been found.", hallId);
                return HallMapper.INSTANCE.mapToDto(hallsRepository.getReferenceById(hallId));
            } else {
                log.warn("Hall with id '{}' doesn't belong to the cinema with id '{}' or not found!", hallId, cinemaId);
                throw new HallNotFoundException(String.format("Hall with id '%d' doesn't belong to the cinema with id '%d' or not found!", hallId, cinemaId));
            }
        } else {
            log.warn("Cinema with id '{}' not found!", cinemaId);
            throw new CinemaNotFoundException(String.format("Cinema with id '%d' not found!", cinemaId));
        }
    }

    @Override
    public HallDto createHall(Long sitTypeId, Hall hall) {
        try {
            hall = hallsRepository.save(hall);
            SitType sitType = sitsTypesRepository.getReferenceById(sitTypeId);
            List<Sit> sits = generateSits(hall, sitType);
            hall.setSits(sits);
            Hall createdHall = hallsRepository.save(hall);
            log.info("New hall with id '{}' has been created.", createdHall.getId());
            return HallMapper.INSTANCE.mapToDto(createdHall);
        } catch (Exception e) {
            log.error("Error was occurred while creating a hall! " + e.getMessage());
            throw new HallCreationException("Error was occurred while creating a hall! " + e.getMessage());
        }
    }

    @Override
    public HallDto updateHall(Hall hall) {
        if (hallsRepository.existsById(hall.getId())) {
            try {
                Hall updatedHall = hallsRepository.save(hall);
                log.info("Hall with id '{}' has been updated.", updatedHall.getId());
                return HallMapper.INSTANCE.mapToDto(updatedHall);
            } catch (Exception e) {
                log.error("Error was occurred while updating a hall! " + e.getMessage());
                throw new HallUpdatingException("Error was occurred while updating a hall! " + e.getMessage());
            }
        } else {
            log.warn("Hall with id '{}' not found!", hall.getId());
            throw new HallNotFoundException(String.format("Hall with id '%d' not found!", hall.getId()));
        }
    }

    @Override
    public Long deleteHallFromCinema(Long cinemaId, Long hallId) {
        if (cinemasRepository.existsById(cinemaId)) {
            Cinema cinema = cinemasRepository.getReferenceById(cinemaId);
            if (cinema.getHalls().stream().anyMatch(hall -> hall.getId().equals(hallId))) {
                try {
                    hallsRepository.deleteById(hallId);
                    log.info("Hall with id '{}' has been deleted.", hallId);
                    return hallId;
                } catch (Exception e) {
                    log.error("Error was occurred while deleting a hall! " + e.getMessage());
                    throw new HallDeletingException("Error was occurred while deleting a hall! " + e.getMessage());
                }
            } else {
                log.warn("Hall with id '{}' doesn't belong to the cinema with id '{}' or not found!", hallId, cinemaId);
                throw new HallNotFoundException(String.format("Hall with id '%d' doesn't belong to the cinema with id '%d' or not found!", hallId, cinemaId));
            }
        } else {
            log.warn("Cinema with id '{}' not found!", cinemaId);
            throw new CinemaNotFoundException(String.format("Cinema with id '%d' not found!", cinemaId));
        }
    }

    @Override
    public List<Long> deleteAllHallsFromCinema(Long cinemaId) {
        if (cinemasRepository.existsById(cinemaId)) {
            try {
                Cinema cinema = cinemasRepository.getReferenceById(cinemaId);
                List<Long> hallsIds = cinema.getHalls().stream().map(Hall::getId).collect(Collectors.toList());
                hallsRepository.deleteAllById(hallsIds);
                log.info("All halls with ids '{}' was deleted from cinema with id '{}'.", hallsIds, cinemaId);
                return hallsIds;
            } catch (Exception e) {
                log.error("Error was occurred while deleting a halls from cinema! " + e.getMessage());
                throw new HallDeletingException("Error was occurred while deleting a halls from cinema! " + e.getMessage());
            }
        } else {
            log.warn("Cinema with id '{}' not found!", cinemaId);
            throw new CinemaNotFoundException(String.format("Cinema with id '%d' not found!", cinemaId));
        }
    }

    @Override
    public List<SitDto> getAllSitsInHall(Long hallId) {
        return null;
    }

    private List<Sit> generateSits(Hall hall, SitType sitType) {
        List<Sit> sits = new ArrayList<>();
        for (int r = 0; r < hall.getRows(); r++) {
            for (int p = 0; p < hall.getPlacesInRow(); p++) {
                Sit sit = new Sit(r, p, sitType, hall);
                Sit createdSit = sitsRepository.save(sit);
                log.info("New sit [{};{}] for a hall with id '{}' has been created.", r, p, hall.getId());
                sits.add(createdSit);
            }
        }
        return sits;
    }
}
