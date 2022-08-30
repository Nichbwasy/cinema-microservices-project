package com.cinema.afisha.microservice.services;

import com.cinema.afisha.microservice.domains.MovieSeanceDto;
import com.cinema.afisha.microservice.domains.ReservationDto;
import com.cinema.afisha.microservice.exceptions.services.reservations.ReservationCreationException;
import com.cinema.afisha.microservice.exceptions.services.reservations.ReservationDeletingException;
import com.cinema.afisha.microservice.exceptions.services.reservations.ReservationNotFoundException;
import com.cinema.afisha.microservice.exceptions.services.reservations.ReservationUpdatingException;
import com.cinema.afisha.microservice.exceptions.services.seances.MovieSeanceCreationException;
import com.cinema.afisha.microservice.exceptions.services.seances.MovieSeanceNotFoundException;
import com.cinema.afisha.microservice.models.MovieSeance;
import com.cinema.afisha.microservice.models.MovieSeanceSit;
import com.cinema.afisha.microservice.models.Reservation;
import com.cinema.afisha.microservice.repositories.MovieSeanceSitsRepository;
import com.cinema.afisha.microservice.repositories.MovieSeancesRepository;
import com.cinema.afisha.microservice.repositories.ReservationsRepository;
import com.cinema.afisha.microservice.services.configs.ReservationsServiceTestsConfiguration;
import com.cinema.afisha.microservice.services.dto.ReservationFormDto;
import com.cinema.cinemas.microservice.domains.CinemaDto;
import com.cinema.cinemas.microservice.domains.HallDto;
import com.cinema.cinemas.microservice.domains.SitDto;
import com.cinema.cinemas.microservice.domains.SitTypeDto;
import com.cinema.films.microservice.domains.DirectorDto;
import com.cinema.films.microservice.domains.FilmDto;
import com.cinema.films.microservice.domains.GenreDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@SpringBootTest(classes = {ReservationsService.class})
@Import(ReservationsServiceTestsConfiguration.class)
@ExtendWith(SpringExtension.class)
public class ReservationsServiceTests {

    private static final ReservationFormDto reservationFormDto = new ReservationFormDto(1L, "mail@mail.com", new Date(), 1L, 1L);
    private static final GenreDto genreDto = new GenreDto(1L, "genre_name");
    private static final DirectorDto directorDto = new DirectorDto(1L, "first_name", "last_name");
    private static final FilmDto filmDto = new FilmDto(1L, "film_name", "description", 2001, null, null);
    private static final CinemaDto cinemaDto = new CinemaDto(1L, "cinema", "description", null);
    private static final HallDto hallDto = new HallDto(1L, "hall_name", "description", 1, 1, 1, null);
    private static final SitDto sitDto = new SitDto(1L, 1, 1, null);
    private static final SitTypeDto sitTypeDto = new SitTypeDto(1L, "sit_type_name", "description", 1);
    private static final MovieSeance movieSeance = new MovieSeance(1L, "CODECODE", new Date(), new Date(), new BigDecimal(1), 1L, 1L, 1L, null);
    private static final MovieSeanceDto movieSeanceDto = new MovieSeanceDto(1L, "CODECODE", new Date(), new Date(), new BigDecimal(1), 1L, 1L, 1L, null);
    private static final MovieSeanceSit movieSeanceSit = new MovieSeanceSit(1L, 1L, "AVAILABLE");
    private static final Reservation reservation = new Reservation(1L, "mail@mail.com", new Date(), null, null);
    private static final ReservationDto reservationDto = new ReservationDto(1L, "mail@mail.com", new Date(), null, null);

    @Autowired
    private ReservationsService reservationsService;

    @MockBean
    private ReservationsRepository reservationsRepository;

    @MockBean
    private MovieSeancesRepository movieSeancesRepository;

    @MockBean
    private MovieSeanceSitsRepository movieSeanceSitsRepository;

    @BeforeAll
    public static void init() {
        filmDto.setGenre(genreDto);
        filmDto.setDirector(directorDto);
        sitDto.setType(sitTypeDto);
        hallDto.setSits(Collections.singletonList(sitDto));
        cinemaDto.setHalls(Collections.singletonList(hallDto));
        movieSeance.setSits(Collections.singletonList(movieSeanceSit));
        reservation.setSit(movieSeanceSit);
        reservation.setMovieSeance(movieSeance);
    }

    @Test
    public void getAllReservationsTest() {
        Mockito.when(reservationsRepository.findAll()).thenReturn(Collections.singletonList(reservation));

        List<ReservationDto> result = reservationsService.getAllReservations();
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(reservationDto.getId(), result.get(0).getId());
        Assertions.assertEquals(reservationDto.getReserverEmail(), result.get(0).getReserverEmail());
        Assertions.assertEquals(movieSeanceDto.getId(), result.get(0).getMovieSeance().getId());
        Assertions.assertEquals(movieSeanceSit.getId(), result.get(0).getSit().getId());
    }

    @Test
    public void getAllReservationsWithRepositoryExceptionTest() {
        Mockito.when(reservationsRepository.findAll()).thenThrow(RuntimeException.class);

        Assertions.assertThrows(RuntimeException.class, () -> reservationsService.getAllReservations());
    }

    @Test
    public void getReservationTest() {
        Mockito.when(reservationsRepository.existsById(1L)).thenReturn(true);
        Mockito.when(reservationsRepository.getReferenceById(1L)).thenReturn(reservation);

        ReservationDto result = reservationsService.getReservation(1L);
        Assertions.assertEquals(reservationDto.getId(), result.getId());
        Assertions.assertEquals(reservationDto.getReserverEmail(), result.getReserverEmail());
        Assertions.assertEquals(movieSeanceDto.getId(), result.getMovieSeance().getId());
        Assertions.assertEquals(movieSeanceSit.getId(), result.getSit().getId());
    }

    @Test
    public void getNotExistedReservationTest() {
        Mockito.when(reservationsRepository.existsById(Mockito.anyLong())).thenReturn(false);

        Assertions.assertThrows(ReservationNotFoundException.class, () -> reservationsService.getReservation(1L));
    }

    @Test
    public void getReservationWithRepositoryExceptionTest() {
        Mockito.when(reservationsRepository.existsById(1L)).thenReturn(true);
        Mockito.when(reservationsRepository.getReferenceById(Mockito.anyLong())).thenThrow(RuntimeException.class);

        Assertions.assertThrows(RuntimeException.class, () -> reservationsService.getReservation(1L));
    }

    @Test
    public void createReservationTest() {
        Mockito.when(movieSeancesRepository.existsById(1L)).thenReturn(true);
        Mockito.when(movieSeancesRepository.getReferenceById(1L)).thenReturn(movieSeance);
        Mockito.when(movieSeanceSitsRepository.getReferenceById(1L)).thenReturn(movieSeanceSit);
        Mockito.when(reservationsRepository.save(Mockito.any())).thenAnswer(a -> a.getArguments()[0]);

        ReservationDto result = reservationsService.createReservation(reservationFormDto);
        Assertions.assertEquals(reservationDto.getReserverEmail(), result.getReserverEmail());
        Assertions.assertEquals(movieSeanceDto.getId(), result.getMovieSeance().getId());
        Assertions.assertEquals(movieSeanceSit.getId(), result.getSit().getId());
    }

    @Test
    public void createReservationWithNotExistedMovieSeanceTest() {
        Mockito.when(movieSeancesRepository.existsById(1L)).thenReturn(false);

        Assertions.assertThrows(MovieSeanceNotFoundException.class, () -> reservationsService.createReservation(reservationFormDto));
    }

    @Test
    public void createReservationWithSeancesRepositoryExceptionSeanceTest() {
        Mockito.when(movieSeancesRepository.existsById(1L)).thenReturn(true);
        Mockito.when(movieSeancesRepository.getReferenceById(Mockito.anyLong())).thenThrow(RuntimeException.class);

        Assertions.assertThrows(RuntimeException.class, () -> reservationsService.createReservation(reservationFormDto));
    }

    @Test
    public void createReservationWithSeanceSitsRepositoryExceptionSeanceTest() {
        Mockito.when(movieSeancesRepository.existsById(1L)).thenReturn(true);
        Mockito.when(movieSeancesRepository.getReferenceById(1L)).thenReturn(movieSeance);
        Mockito.when(movieSeanceSitsRepository.getReferenceById(Mockito.anyLong())).thenThrow(RuntimeException.class);

        Assertions.assertThrows(RuntimeException.class, () -> reservationsService.createReservation(reservationFormDto));
    }

    @Test
    public void createReservationWithReservationsRepositoryExceptionSeanceTest() {
        Mockito.when(movieSeancesRepository.existsById(1L)).thenReturn(true);
        Mockito.when(movieSeancesRepository.getReferenceById(1L)).thenReturn(movieSeance);
        Mockito.when(movieSeanceSitsRepository.getReferenceById(1L)).thenReturn(movieSeanceSit);
        Mockito.when(reservationsRepository.save(Mockito.any())).thenThrow(RuntimeException.class);

        Assertions.assertThrows(ReservationCreationException.class, () -> reservationsService.createReservation(reservationFormDto));
    }

    @Test
    public void updateReservationTest() {
        Mockito.when(reservationsRepository.existsById(1L)).thenReturn(true);
        Mockito.when(reservationsRepository.getReferenceById(1L)).thenReturn(reservation);
        Mockito.when(movieSeancesRepository.existsById(1L)).thenReturn(true);
        Mockito.when(movieSeancesRepository.getReferenceById(1L)).thenReturn(movieSeance);
        Mockito.when(movieSeanceSitsRepository.getReferenceById(1L)).thenReturn(movieSeanceSit);
        Mockito.when(reservationsRepository.save(Mockito.any())).thenAnswer(a -> a.getArguments()[0]);

        ReservationDto result = reservationsService.updateReservation(reservationFormDto);
        Assertions.assertEquals(reservationDto.getReserverEmail(), result.getReserverEmail());
        Assertions.assertEquals(movieSeanceDto.getId(), result.getMovieSeance().getId());
        Assertions.assertEquals(movieSeanceSit.getId(), result.getSit().getId());
    }

    @Test
    public void updateNotExistedReservationTest() {
        Mockito.when(reservationsRepository.existsById(1L)).thenReturn(false);

        Assertions.assertThrows(ReservationNotFoundException.class, () -> reservationsService.updateReservation(reservationFormDto));
    }

    @Test
    public void updateReservationWithNotExistedMovieSeanceTest() {
        Mockito.when(reservationsRepository.existsById(1L)).thenReturn(true);
        Mockito.when(reservationsRepository.getReferenceById(1L)).thenReturn(reservation);
        Mockito.when(movieSeancesRepository.existsById(1L)).thenReturn(false);

        Assertions.assertThrows(MovieSeanceNotFoundException.class, () -> reservationsService.updateReservation(reservationFormDto));
    }

    @Test
    public void updateReservationWithSeancesRepositoryExceptionSeanceTest() {
        Mockito.when(reservationsRepository.existsById(1L)).thenReturn(true);
        Mockito.when(reservationsRepository.getReferenceById(1L)).thenReturn(reservation);
        Mockito.when(movieSeancesRepository.existsById(1L)).thenReturn(true);
        Mockito.when(movieSeancesRepository.getReferenceById(Mockito.anyLong())).thenThrow(RuntimeException.class);

        Assertions.assertThrows(RuntimeException.class, () -> reservationsService.updateReservation(reservationFormDto));
    }

    @Test
    public void updateReservationWithSeanceSitsRepositoryExceptionSeanceTest() {
        Mockito.when(reservationsRepository.existsById(1L)).thenReturn(true);
        Mockito.when(reservationsRepository.getReferenceById(1L)).thenReturn(reservation);
        Mockito.when(movieSeancesRepository.existsById(1L)).thenReturn(true);
        Mockito.when(movieSeancesRepository.getReferenceById(1L)).thenReturn(movieSeance);
        Mockito.when(movieSeanceSitsRepository.getReferenceById(Mockito.anyLong())).thenThrow(RuntimeException.class);

        Assertions.assertThrows(RuntimeException.class, () -> reservationsService.updateReservation(reservationFormDto));
    }

    @Test
    public void updateReservationWithReservationsRepositoryExceptionSeanceTest() {
        Mockito.when(reservationsRepository.existsById(1L)).thenReturn(true);
        Mockito.when(reservationsRepository.getReferenceById(Mockito.anyLong())).thenThrow(RuntimeException.class);

        Assertions.assertThrows(RuntimeException.class, () -> reservationsService.updateReservation(reservationFormDto));
    }

    @Test
    public void deleteReservationTest() {
        Mockito.when(reservationsRepository.existsById(1L)).thenReturn(true);
        Mockito.when(reservationsRepository.getReferenceById(1L)).thenReturn(reservation);
        Mockito.doNothing().when(reservationsRepository).deleteById(1L);

        Assertions.assertEquals(1L, reservationsService.deleteReservation(1L));
    }

    @Test
    public void deleteNotExistedReservationTest() {
        Mockito.when(reservationsRepository.existsById(1L)).thenReturn(false);

        Assertions.assertThrows(ReservationNotFoundException.class, () -> reservationsService.deleteReservation(1L));
    }

    @Test
    public void deleteReservationWithServiceExceptionTest() {
        Mockito.when(reservationsRepository.existsById(1L)).thenReturn(true);
        Mockito.when(reservationsRepository.getReferenceById(1L)).thenReturn(reservation);
        Mockito.doThrow(RuntimeException.class).when(reservationsRepository).deleteById(Mockito.anyLong());

        Assertions.assertThrows(ReservationDeletingException.class, () -> reservationsService.deleteReservation(1L));
    }
}
