package com.cinema.afisha.microservice.services;

import com.cinema.afisha.microservice.domains.MovieSeanceDto;
import com.cinema.afisha.microservice.exceptions.services.seances.MovieSeanceDeletingException;
import com.cinema.afisha.microservice.exceptions.services.seances.MovieSeanceNotFoundException;
import com.cinema.afisha.microservice.exceptions.services.seances.api.MovieSeanceApiException;
import com.cinema.afisha.microservice.models.MovieSeance;
import com.cinema.afisha.microservice.models.MovieSeanceSit;
import com.cinema.afisha.microservice.repositories.MovieSeanceSitsRepository;
import com.cinema.afisha.microservice.repositories.MovieSeancesRepository;
import com.cinema.afisha.microservice.services.configs.MovieSeancesServiceTestsConfiguration;
import com.cinema.afisha.microservice.services.dto.SeanceDto;
import com.cinema.cinemas.microservice.clients.CinemasApiClient;
import com.cinema.cinemas.microservice.domains.CinemaDto;
import com.cinema.cinemas.microservice.domains.HallDto;
import com.cinema.cinemas.microservice.domains.SitDto;
import com.cinema.cinemas.microservice.domains.SitTypeDto;
import com.cinema.cinemas.microservice.exceptions.clients.CinemasMicroserviceApiException;
import com.cinema.cinemas.microservice.exceptions.services.halls.HallNotBelongCinemaException;
import com.cinema.films.microservice.clients.FilmsApiClient;
import com.cinema.films.microservice.domains.DirectorDto;
import com.cinema.films.microservice.domains.FilmDto;
import com.cinema.films.microservice.domains.GenreDto;
import com.cinema.films.microservice.exceptions.clients.FilmsMicroserviceApiException;
import com.cinema.films.microservice.exceptions.services.films.FilmNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@SpringBootTest(classes = {MovieSeancesService.class})
@Import(MovieSeancesServiceTestsConfiguration.class)
@ExtendWith(SpringExtension.class)
public class MovieSeancesServiceTests {

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

    @Autowired
    private MovieSeancesService movieSeancesService;

    @MockBean
    private MovieSeancesRepository movieSeancesRepository;

    @MockBean
    private MovieSeanceSitsRepository movieSeanceSitsRepository;

    @MockBean
    private CinemasApiClient cinemasApiClient;

    @MockBean
    private FilmsApiClient filmsApiClient;

    @BeforeAll
    public static void init() {
        filmDto.setGenre(genreDto);
        filmDto.setDirector(directorDto);
        sitDto.setType(sitTypeDto);
        hallDto.setSits(Collections.singletonList(sitDto));
        cinemaDto.setHalls(Collections.singletonList(hallDto));
    }

    @Test
    public void getPageOfSeancesTest() {
        InputStream inputStream = Mockito.mock(InputStream.class);

        Page<MovieSeance> filmPage = new PageImpl(Collections.singletonList(movieSeance));
        Mockito.when(movieSeancesRepository.findAll(Mockito.any(Pageable.class))).thenReturn(filmPage);
        Mockito.when(filmsApiClient.getFilm(1L)).thenReturn(filmDto);
        Mockito.when(cinemasApiClient.getCinemaHall(1L, 1L)).thenReturn(hallDto);
        Mockito.when(filmsApiClient.getFilmPoster(1L)).thenReturn(inputStream);

        List<SeanceDto> result = movieSeancesService.getPageOfSeances(1);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(movieSeanceDto.getId(), result.get(0).getMovieSeance().getId());
        Assertions.assertEquals(movieSeanceDto.getCode(), result.get(0).getMovieSeance().getCode());
        Assertions.assertEquals(movieSeanceDto.getCinemaId(), result.get(0).getMovieSeance().getCinemaId());
        Assertions.assertEquals(movieSeanceDto.getHallId(), result.get(0).getMovieSeance().getHallId());
        Assertions.assertEquals(movieSeanceDto.getFilmId(), result.get(0).getMovieSeance().getFilmId());
        Assertions.assertEquals(movieSeanceDto.getPrice(), result.get(0).getMovieSeance().getPrice());
        Assertions.assertEquals(filmDto.getId(), result.get(0).getFilm().getId());
        Assertions.assertEquals(filmDto.getName(), result.get(0).getFilm().getName());
        Assertions.assertEquals(filmDto.getDescription(), result.get(0).getFilm().getDescription());
        Assertions.assertEquals(filmDto.getYear(), result.get(0).getFilm().getYear());
        Assertions.assertEquals(hallDto.getId(), result.get(0).getHall().getId());
        Assertions.assertEquals(hallDto.getName(), result.get(0).getHall().getName());
        Assertions.assertEquals(hallDto.getDescription(), result.get(0).getHall().getDescription());
        Assertions.assertEquals(hallDto.getSize(), result.get(0).getHall().getSize());
        Assertions.assertEquals(hallDto.getRows(), result.get(0).getHall().getRows());
        Assertions.assertEquals(hallDto.getPlacesInRow(), result.get(0).getHall().getPlacesInRow());
    }

    @Test
    public void getPageOfSeancesWithRepositoryExceptionTest() {
        Mockito.when(movieSeancesRepository.findAll(Mockito.any(Pageable.class))).thenThrow(RuntimeException.class);

        Assertions.assertThrows(RuntimeException.class, () -> movieSeancesService.getPageOfSeances(1));
    }

    @Test
    public void getPageOfSeancesWithCinemasClientExceptionTest() {
        Page<MovieSeance> filmPage = new PageImpl(Collections.singletonList(movieSeance));
        Mockito.when(movieSeancesRepository.findAll(Mockito.any(Pageable.class))).thenReturn(filmPage);
        Mockito.when(filmsApiClient.getFilm(1L)).thenReturn(filmDto);
        Mockito.when(cinemasApiClient.getCinemaHall(1L, 1L)).thenThrow(CinemasMicroserviceApiException.class);

        Assertions.assertThrows(MovieSeanceApiException.class, () -> movieSeancesService.getPageOfSeances(1));
    }

    @Test
    public void getPageOfSeancesWithFilmsClientExceptionTest() {
        Page<MovieSeance> filmPage = new PageImpl(Collections.singletonList(movieSeance));
        Mockito.when(movieSeancesRepository.findAll(Mockito.any(Pageable.class))).thenReturn(filmPage);
        Mockito.when(filmsApiClient.getFilm(1L)).thenReturn(filmDto);
        Mockito.when(cinemasApiClient.getCinemaHall(1L, 1L)).thenReturn(hallDto);
        Mockito.when(filmsApiClient.getFilmPoster(1L)).thenThrow(FilmsMicroserviceApiException.class);

        Assertions.assertThrows(MovieSeanceApiException.class, () -> movieSeancesService.getPageOfSeances(1));
    }

    @Test
    public void getSeanceTest() {
        InputStream inputStream = Mockito.mock(InputStream.class);

        Mockito.when(movieSeancesRepository.existsById(1L)).thenReturn(true);
        Mockito.when(movieSeancesRepository.getReferenceById(1L)).thenReturn(movieSeance);
        Mockito.when(filmsApiClient.getFilm(1L)).thenReturn(filmDto);
        Mockito.when(cinemasApiClient.getCinemaHall(1L, 1L)).thenReturn(hallDto);
        Mockito.when(filmsApiClient.getFilmPoster(1L)).thenReturn(inputStream);

        SeanceDto result = movieSeancesService.getSeance(1L);
        Assertions.assertEquals(movieSeanceDto.getId(), result.getMovieSeance().getId());
        Assertions.assertEquals(movieSeanceDto.getCode(), result.getMovieSeance().getCode());
        Assertions.assertEquals(movieSeanceDto.getCinemaId(), result.getMovieSeance().getCinemaId());
        Assertions.assertEquals(movieSeanceDto.getHallId(), result.getMovieSeance().getHallId());
        Assertions.assertEquals(movieSeanceDto.getFilmId(), result.getMovieSeance().getFilmId());
        Assertions.assertEquals(movieSeanceDto.getPrice(), result.getMovieSeance().getPrice());
        Assertions.assertEquals(filmDto.getId(), result.getFilm().getId());
        Assertions.assertEquals(filmDto.getName(), result.getFilm().getName());
        Assertions.assertEquals(filmDto.getDescription(), result.getFilm().getDescription());
        Assertions.assertEquals(filmDto.getYear(), result.getFilm().getYear());
        Assertions.assertEquals(hallDto.getId(), result.getHall().getId());
        Assertions.assertEquals(hallDto.getName(), result.getHall().getName());
        Assertions.assertEquals(hallDto.getDescription(), result.getHall().getDescription());
        Assertions.assertEquals(hallDto.getSize(), result.getHall().getSize());
        Assertions.assertEquals(hallDto.getRows(), result.getHall().getRows());
        Assertions.assertEquals(hallDto.getPlacesInRow(), result.getHall().getPlacesInRow());
    }

    @Test
    public void getNotExistedSeanceTest() {
        Mockito.when(movieSeancesRepository.existsById(1L)).thenReturn(false);

        Assertions.assertThrows(MovieSeanceNotFoundException.class, () -> movieSeancesService.getSeance(1L));
    }

    @Test
    public void getSeanceWithServiceExceptionTest() {
        Mockito.when(movieSeancesRepository.existsById(1L)).thenReturn(true);
        Mockito.when(movieSeancesRepository.getReferenceById(1L)).thenThrow(RuntimeException.class);

        Assertions.assertThrows(RuntimeException.class, () -> movieSeancesService.getSeance(1L));
    }

    @Test
    public void getSeanceWithFilmsClientExceptionTest() {
        Mockito.when(movieSeancesRepository.existsById(1L)).thenReturn(true);
        Mockito.when(movieSeancesRepository.getReferenceById(1L)).thenReturn(movieSeance);
        Mockito.when(filmsApiClient.getFilm(1L)).thenThrow(FilmsMicroserviceApiException.class);

        Assertions.assertThrows(MovieSeanceApiException.class, () -> movieSeancesService.getSeance(1L));
    }

    @Test
    public void getSeanceWithCinemasClientExceptionTest() {
        Mockito.when(movieSeancesRepository.existsById(1L)).thenReturn(true);
        Mockito.when(movieSeancesRepository.getReferenceById(1L)).thenReturn(movieSeance);
        Mockito.when(filmsApiClient.getFilm(1L)).thenReturn(filmDto);
        Mockito.when(cinemasApiClient.getCinemaHall(1L, 1L)).thenThrow(CinemasMicroserviceApiException.class);

        Assertions.assertThrows(MovieSeanceApiException.class, () -> movieSeancesService.getSeance(1L));
    }

    @Test
    public void createSeanceTest() {
        Mockito.when(cinemasApiClient.getCinemaHall(1L, 1L)).thenReturn(hallDto);
        Mockito.when(filmsApiClient.getFilm(1L)).thenReturn(filmDto);
        Mockito.when(movieSeanceSitsRepository.save(Mockito.any())).thenAnswer(a -> a.getArguments()[0]);
        Mockito.when(movieSeancesRepository.save(Mockito.any())).thenAnswer(a -> a.getArguments()[0]);

        MovieSeanceDto result = movieSeancesService.createSeance(movieSeance);
        Assertions.assertEquals(movieSeanceDto.getId(), result.getId());
        Assertions.assertEquals(movieSeanceDto.getCinemaId(), result.getCinemaId());
        Assertions.assertEquals(movieSeanceDto.getHallId(), result.getHallId());
        Assertions.assertEquals(movieSeanceDto.getFilmId(), result.getFilmId());
        Assertions.assertEquals(movieSeanceDto.getPrice(), result.getPrice());
    }

    @Test
    public void createSeanceWhenCinemaNotFoundTest() {
        Mockito.when(cinemasApiClient.getCinemaHall(1L, 1L)).thenReturn(null);

        Assertions.assertThrows(HallNotBelongCinemaException.class, () -> movieSeancesService.createSeance(movieSeance));
    }

    @Test
    public void createSeanceWhenFilmNotFoundTest() {
        Mockito.when(cinemasApiClient.getCinemaHall(1L, 1L)).thenReturn(hallDto);
        Mockito.when(filmsApiClient.getFilm(1L)).thenReturn(null);

        Assertions.assertThrows(FilmNotFoundException.class, () -> movieSeancesService.createSeance(movieSeance));
    }

    @Test
    public void createSeanceWithRepositoryExceptionTest() {
        Mockito.when(cinemasApiClient.getCinemaHall(1L, 1L)).thenReturn(hallDto);
        Mockito.when(filmsApiClient.getFilm(1L)).thenReturn(filmDto);
        Mockito.when(movieSeanceSitsRepository.save(Mockito.any())).thenThrow(RuntimeException.class);

        Assertions.assertThrows(RuntimeException.class, () -> movieSeancesService.createSeance(movieSeance));
    }

    @Test
    public void deleteSeanceTest() {
        Mockito.when(movieSeancesRepository.existsById(1L)).thenReturn(true);
        Mockito.doNothing().when(movieSeancesRepository).deleteById(1L);

        Assertions.assertEquals(1L, movieSeancesService.deleteSeance(1L));
    }

    @Test
    public void deleteNotExistedSeanceTest() {
        Mockito.when(movieSeancesRepository.existsById(Mockito.anyLong())).thenReturn(false);

        Assertions.assertThrows(MovieSeanceNotFoundException.class, () -> movieSeancesService.deleteSeance(1L));
    }

    @Test
    public void deleteSeanceWithRepositoryExceptionTest() {
        Mockito.when(movieSeancesRepository.existsById(1L)).thenReturn(true);
        Mockito.doThrow(RuntimeException.class).when(movieSeancesRepository).deleteById(1L);

        Assertions.assertThrows(MovieSeanceDeletingException.class, () -> movieSeancesService.deleteSeance(1L));
    }

}
