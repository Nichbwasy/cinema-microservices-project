package com.cinema.afisha.microservice.services.imliments;

import com.cinema.afisha.microservice.domains.ReservationDto;
import com.cinema.afisha.microservice.exceptions.services.reservations.ReservationCreationException;
import com.cinema.afisha.microservice.exceptions.services.reservations.ReservationNotFoundException;
import com.cinema.afisha.microservice.exceptions.services.reservations.ReservationUpdatingException;
import com.cinema.afisha.microservice.exceptions.services.seances.sits.MovieSeanceSitNotFoundException;
import com.cinema.afisha.microservice.models.MovieSeance;
import com.cinema.afisha.microservice.models.MovieSeanceSit;
import com.cinema.afisha.microservice.models.Reservation;
import com.cinema.afisha.microservice.repositories.MovieSeanceSitsRepository;
import com.cinema.afisha.microservice.repositories.MovieSeancesRepository;
import com.cinema.afisha.microservice.repositories.ReservationsRepository;
import com.cinema.afisha.microservice.services.ReservationsService;
import com.cinema.afisha.microservice.services.dto.ReservationFormDto;
import com.cinema.afisha.microservice.services.mappers.ReservationMapper;
import com.cinema.common.utils.contants.movie.seances.SitsStatuses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class ReservationsServiceImpl implements ReservationsService {

    @Autowired
    private MovieSeancesRepository movieSeancesRepository;

    @Autowired
    private MovieSeanceSitsRepository movieSeanceSitsRepository;

    @Autowired
    private ReservationsRepository reservationsRepository;

    @Override
    public List<ReservationDto> getAllReservations() {
        List<Reservation> reservations = reservationsRepository.findAll();
        log.info("'{}' reservations was found.", reservations.size());
        return reservations.stream().map(ReservationMapper.INSTANCE::mapToDto).collect(Collectors.toList());
    }

    @Override
    public ReservationDto getReservation(Long id) {
        if (reservationsRepository.existsById(id)) {
            Reservation reservation = reservationsRepository.getReferenceById(id);
            log.info("Reservation with id '{}' has been found.", id);
            return ReservationMapper.INSTANCE.mapToDto(reservation);
        } else {
            log.warn("Reservation with id '{}' doesn't exist.", id);
            throw new ReservationNotFoundException(String.format("Reservation with id '%s' doesn't exist.", id));
        }
    }

    @Override
    public ReservationDto getReservation(String email) {
        if (reservationsRepository.existsByReserverEmail(email)) {
            Reservation reservation = reservationsRepository.getByReserverEmail(email);
            log.info("Reservation with email '{}' has been found.", email);
            return ReservationMapper.INSTANCE.mapToDto(reservation);
        } else {
            log.warn("Reservation with email '{}' doesn't exist.", email);
            throw new ReservationNotFoundException(String.format("Reservation with email '%s' doesn't exist.", email));
        }
    }

    @Override
    public ReservationDto createReservation(ReservationFormDto reservation) {
        if (movieSeancesRepository.existsById(reservation.getId())) {
            MovieSeance movieSeance = movieSeancesRepository.getReferenceById(reservation.getMovieSeanceId());
            if (movieSeance.getSits().stream().anyMatch(sit -> sit.getId().equals(reservation.getSitId()))) {
                try {
                    MovieSeanceSit movieSeanceSit = movieSeanceSitsRepository.getReferenceById(reservation.getSitId());
                    Reservation createdReservation = new Reservation();

                    movieSeanceSit.setStatus(SitsStatuses.OCCUPIED);
                    createdReservation.setReservationTime(new Date());
                    createdReservation.setMovieSeance(movieSeance);
                    createdReservation.setSit(movieSeanceSit);
                    createdReservation.setReserverEmail(reservation.getReserverEmail());
                    createdReservation = reservationsRepository.save(createdReservation);
                    log.info("New reservation with id '{}' has been created.", createdReservation.getId());
                    return ReservationMapper.INSTANCE.mapToDto(createdReservation);
                } catch (Exception e) {
                    log.error("Can't create reservation! " + e.getMessage());
                    throw new ReservationCreationException("Can't create reservation! " + e.getMessage());
                }
            } else {
                log.warn("Movie seance sit with id '{}' doesn't belong to the hall or not found!", reservation.getSitId());
                throw new MovieSeanceSitNotFoundException(String.format("Movie seance sit with id '%d' doesn't belong to the hall or not found!", reservation.getSitId()));
            }
        } else {
            log.warn("Movie seance with id '{}' not found!", reservation.getMovieSeanceId());
            throw new MovieSeanceSitNotFoundException(String.format("Movie seance with id '%d' not found!", reservation.getMovieSeanceId()));
        }
    }

    @Override
    public ReservationDto updateReservation(ReservationFormDto reservation) {
        if (reservationsRepository.existsById(reservation.getId())) {
            Reservation savedReservation = reservationsRepository.getReferenceById(reservation.getId());
            if (movieSeancesRepository.existsById(reservation.getId())) {
                MovieSeance movieSeance = movieSeancesRepository.getReferenceById(reservation.getMovieSeanceId());
                if (movieSeance.getSits().stream().anyMatch(sit -> sit.getId().equals(reservation.getSitId()))) {
                    try {
                        MovieSeanceSit movieSeanceSit = movieSeanceSitsRepository.getReferenceById(reservation.getSitId());
                        savedReservation.setReservationTime(new Date());
                        savedReservation.setMovieSeance(movieSeance);
                        savedReservation.setSit(movieSeanceSit);
                        savedReservation.setReserverEmail(reservation.getReserverEmail());
                        log.info("Reservation with id '{}' has been updated.", savedReservation.getId());
                        return ReservationMapper.INSTANCE.mapToDto(savedReservation);
                    } catch (Exception e) {
                        log.error("Can't update reservation! " + e.getMessage());
                        throw new ReservationUpdatingException("Can't update reservation! " + e.getMessage());
                    }
                } else {
                    log.warn("Movie seance sit with id '{}' doesn't belong to the hall or not found!", reservation.getSitId());
                    throw new MovieSeanceSitNotFoundException(String.format("Movie seance sit with id '%d' doesn't belong to the hall or not found!", reservation.getSitId()));
                }
            } else {
                log.warn("Movie seance with id '{}' not found!", reservation.getMovieSeanceId());
                throw new MovieSeanceSitNotFoundException(String.format("Movie seance with id '%d' not found!", reservation.getMovieSeanceId()));
            }
        } else {
            log.warn("Reservation with id '{}' not found!", reservation.getId());
            throw new MovieSeanceSitNotFoundException(String.format("Reservation with id '%d' not found!", reservation.getId()));
        }
    }

    @Override
    public Long deleteReservation(Long id) {
        if (reservationsRepository.existsById(id)) {
            try {
                Reservation reservation = reservationsRepository.getReferenceById(id);
                MovieSeanceSit sit = reservation.getSit();
                sit.setStatus(SitsStatuses.AVAILABLE);
                reservationsRepository.deleteById(id);
                log.info("Reservation with id '{}' has been deleted.", id);
                return id;
            } catch (Exception e) {
                log.error("Can't delete reservation! " + e.getMessage());
                throw new ReservationUpdatingException("Can't delete reservation! " + e.getMessage());
            }
        } else {
            log.warn("Reservation with id '{}' not found!", id);
            throw new MovieSeanceSitNotFoundException(String.format("Reservation with id '%d' not found!", id));
        }
    }
}
