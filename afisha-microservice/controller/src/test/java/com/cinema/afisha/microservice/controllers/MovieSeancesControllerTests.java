package com.cinema.afisha.microservice.controllers;

import com.cinema.afisha.microservice.controllers.configs.AfishaControllersTestConfiguration;
import com.cinema.afisha.microservice.domains.MovieSeanceDto;
import com.cinema.afisha.microservice.domains.ReservationDto;
import com.cinema.afisha.microservice.exceptions.services.seances.MovieSeanceCreationException;
import com.cinema.afisha.microservice.exceptions.services.seances.MovieSeanceDeletingException;
import com.cinema.afisha.microservice.exceptions.services.seances.MovieSeanceNotFoundException;
import com.cinema.afisha.microservice.exceptions.services.seances.api.MovieSeanceApiException;
import com.cinema.afisha.microservice.models.MovieSeance;
import com.cinema.afisha.microservice.models.MovieSeanceSit;
import com.cinema.afisha.microservice.models.Reservation;
import com.cinema.afisha.microservice.services.MovieSeancesService;
import com.cinema.afisha.microservice.services.dto.ReservationFormDto;
import com.cinema.afisha.microservice.services.dto.SeanceDto;
import com.cinema.cinemas.microservice.domains.CinemaDto;
import com.cinema.cinemas.microservice.domains.HallDto;
import com.cinema.cinemas.microservice.domains.SitDto;
import com.cinema.cinemas.microservice.domains.SitTypeDto;
import com.cinema.cinemas.microservice.exceptions.services.halls.HallNotBelongCinemaException;
import com.cinema.films.microservice.domains.DirectorDto;
import com.cinema.films.microservice.domains.FilmDto;
import com.cinema.films.microservice.domains.GenreDto;
import com.cinema.films.microservice.exceptions.services.films.FilmNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsInAnyOrder;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {MovieSeancesController.class})
@Import(AfishaControllersTestConfiguration.class)
@AutoConfigureMockMvc( addFilters = false ) // disable security
public class MovieSeancesControllerTests {

    private static final FilmDto filmDto = new FilmDto(1L, "film_name", "description", 2001, null, null);
    private static final HallDto hallDto = new HallDto(1L, "hall_name", "description", 1, 1, 1, null);
    private static final MovieSeance movieSeance = new MovieSeance(1L, "CODECODECODECODE", new Date(), new Date(), new BigDecimal(1), 1L, 1L, 1L, null);
    private static final MovieSeanceDto movieSeanceDto = new MovieSeanceDto(1L, "CODECODECODECODE", new Date(), new Date(), new BigDecimal(1), 1L, 1L, 1L, null);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieSeancesService movieSeancesService;

    @Test
    public void getSeancesPageTest() throws Exception {
        SeanceDto seanceDto = new SeanceDto(movieSeanceDto, filmDto, hallDto, null);
        Mockito.when(movieSeancesService.getPageOfSeances(0)).thenReturn(Collections.singletonList(seanceDto));

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/afisha/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].movieSeance.id", containsInAnyOrder(movieSeanceDto.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].movieSeance.code", containsInAnyOrder(movieSeanceDto.getCode())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].movieSeance.cinemaId", containsInAnyOrder(movieSeanceDto.getCinemaId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].movieSeance.hallId", containsInAnyOrder(movieSeanceDto.getHallId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].movieSeance.filmId", containsInAnyOrder(movieSeanceDto.getFilmId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].film.id", containsInAnyOrder(filmDto.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].film.name", containsInAnyOrder(filmDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].hall.id", containsInAnyOrder(hallDto.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].hall.name", containsInAnyOrder(hallDto.getName())));
    }

    @Test
    public void getSeancesPageWithServiceExceptionTest() throws Exception {
        SeanceDto seanceDto = new SeanceDto(movieSeanceDto, filmDto, hallDto, null);
        Mockito.when(movieSeancesService.getPageOfSeances(Mockito.anyInt())).thenThrow(RuntimeException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/afisha/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getSeanceTest() throws Exception {
        SeanceDto seanceDto = new SeanceDto(movieSeanceDto, filmDto, hallDto, null);
        Mockito.when(movieSeancesService.getSeance(1L)).thenReturn(seanceDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                    .get("/afisha/seance/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieSeance.id").value(movieSeanceDto.getId().intValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieSeance.code").value(movieSeanceDto.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieSeance.cinemaId").value(movieSeanceDto.getCinemaId().intValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieSeance.hallId").value(movieSeanceDto.getHallId().intValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieSeance.filmId").value(movieSeanceDto.getFilmId().intValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.film.id").value(filmDto.getId().intValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.film.name").value(filmDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.hall.id").value(hallDto.getId().intValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.hall.name").value(hallDto.getName()));
    }

    @Test
    public void getNotExistedSeanceTest() throws Exception {
        SeanceDto seanceDto = new SeanceDto(movieSeanceDto, filmDto, hallDto, null);
        Mockito.when(movieSeancesService.getSeance(1L)).thenThrow(MovieSeanceNotFoundException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/afisha/seance/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getSeanceWithApiExceptionTest() throws Exception {
        SeanceDto seanceDto = new SeanceDto(movieSeanceDto, filmDto, hallDto, null);
        Mockito.when(movieSeancesService.getSeance(1L)).thenThrow(MovieSeanceApiException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/afisha/seance/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getSeanceWithServiceExceptionTest() throws Exception {
        SeanceDto seanceDto = new SeanceDto(movieSeanceDto, filmDto, hallDto, null);
        Mockito.when(movieSeancesService.getSeance(1L)).thenThrow(RuntimeException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/afisha/seance/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createSeanceTest() throws Exception {
        Mockito.when(movieSeancesService.createSeance(movieSeance)).thenReturn(movieSeanceDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/afisha")
                        .flashAttr("movieSeance", movieSeance))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(movieSeanceDto.getId().intValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(movieSeanceDto.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cinemaId").value(movieSeanceDto.getCinemaId().intValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.hallId").value(movieSeanceDto.getHallId().intValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.filmId").value(movieSeanceDto.getFilmId().intValue()));
    }

    @Test
    public void createNotValidSeanceTest() throws Exception {
        MovieSeance notValidMovieSeance = new MovieSeance(1L, "NOTVALID", new Date(), new Date(), new BigDecimal(1), 1L, 1L, 1L, null);

        Mockito.when(movieSeancesService.createSeance(notValidMovieSeance)).thenReturn(new MovieSeanceDto());

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/afisha")
                        .flashAttr("movieSeance", notValidMovieSeance))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void createSeanceWithHallNotBelongCinemaExceptionTest() throws Exception {
        Mockito.when(movieSeancesService.createSeance(Mockito.any(MovieSeance.class))).thenThrow(HallNotBelongCinemaException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/afisha")
                        .flashAttr("movieSeance", movieSeance))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createSeanceWithFilmNotFoundExceptionTest() throws Exception {
        Mockito.when(movieSeancesService.createSeance(Mockito.any(MovieSeance.class))).thenThrow(FilmNotFoundException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/afisha")
                        .flashAttr("movieSeance", movieSeance))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createSeanceWithServiceExceptionTest() throws Exception {
        Mockito.when(movieSeancesService.createSeance(Mockito.any(MovieSeance.class))).thenThrow(MovieSeanceCreationException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/afisha")
                        .flashAttr("movieSeance", movieSeance))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteSeanceTest() throws Exception {
        Mockito.when(movieSeancesService.deleteSeance(1L)).thenReturn(1L);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/afisha/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(1));
    }

    @Test
    public void deleteNotExistedSeanceTest() throws Exception {
        Mockito.when(movieSeancesService.deleteSeance(1L)).thenThrow(MovieSeanceNotFoundException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/afisha/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteSeanceWithServiceExceptionTest() throws Exception {
        Mockito.when(movieSeancesService.deleteSeance(Mockito.anyLong())).thenThrow(MovieSeanceDeletingException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/afisha/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

}
