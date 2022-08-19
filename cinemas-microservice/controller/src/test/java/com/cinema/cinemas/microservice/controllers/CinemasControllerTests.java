package com.cinema.cinemas.microservice.controllers;

import com.cinema.cinemas.microservice.controllers.configs.ControllersTestConfiguration;
import com.cinema.cinemas.microservice.domains.CinemaDto;
import com.cinema.cinemas.microservice.domains.HallDto;
import com.cinema.cinemas.microservice.domains.SitDto;
import com.cinema.cinemas.microservice.domains.SitTypeDto;
import com.cinema.cinemas.microservice.exceptions.services.cinemas.*;
import com.cinema.cinemas.microservice.exceptions.services.halls.HallCreationException;
import com.cinema.cinemas.microservice.exceptions.services.sits.types.SitTypeNotFoundException;
import com.cinema.cinemas.microservice.models.Cinema;
import com.cinema.cinemas.microservice.models.Hall;
import com.cinema.cinemas.microservice.models.Sit;
import com.cinema.cinemas.microservice.models.SitType;
import com.cinema.cinemas.microservice.services.CinemasService;
import org.junit.jupiter.api.BeforeAll;
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

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.containsInAnyOrder;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {CinemaController.class})
@Import(ControllersTestConfiguration.class)
@AutoConfigureMockMvc( addFilters = false ) // disable security
public class CinemasControllerTests {

    private static Cinema cinema = new Cinema(1L, "cinema", "description", null);
    private static CinemaDto cinemaDto = new CinemaDto(1L, "cinema", "description", null);
    private static Hall hall = new Hall(1L, "hall_name", "description", 1, 1, 1, null);
    private static HallDto hallDto = new HallDto(1L, "hall_name", "description", 1, 1, 1, null);
    private static Sit sit = new Sit(1L, 1, 1, null);
    private static SitDto sitDto = new SitDto(1L, 1, 1, null);
    private static SitType sitType = new SitType(1L, "sit_type_name", "description", 1);
    private static SitTypeDto sitTypeDto = new SitTypeDto(1L, "sit_type_name", "description", 1);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CinemasService cinemasService;

    @BeforeAll
    public static void init() {
        sit.setType(sitType);
        sitDto.setType(sitTypeDto);
        hall.setSits(Collections.singletonList(sit));
        hallDto.setSits(Collections.singletonList(sitDto));
        cinema.setHalls(Collections.singletonList(hall));
        cinemaDto.setHalls(Collections.singletonList(hallDto));
    }

    @Test
    public void getAllCinemasTest() throws Exception {
        Mockito.when(cinemasService.getAllCinemas()).thenReturn(Collections.singletonList(cinemaDto));

        this.mockMvc
                .perform(MockMvcRequestBuilders
                    .get("/cinemas"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id", containsInAnyOrder(cinema.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name", containsInAnyOrder(cinema.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].description", containsInAnyOrder(cinema.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].halls.[*].id", containsInAnyOrder(hall.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].halls.[*].name", containsInAnyOrder(hall.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].halls.[*].size", containsInAnyOrder(hall.getSize())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].halls.[*].rows", containsInAnyOrder(hall.getRows())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].halls.[*].description", containsInAnyOrder(hall.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].halls.[*].placesInRow", containsInAnyOrder(hall.getPlacesInRow())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].halls.[*].sits.[*].id", containsInAnyOrder(sit.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].halls.[*].sits.[*].row", containsInAnyOrder(sit.getRow())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].halls.[*].sits.[*].place", containsInAnyOrder(sit.getPlace())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].halls.[*].sits.[*].type.id", containsInAnyOrder(sitType.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].halls.[*].sits.[*].type.name", containsInAnyOrder(sitType.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].halls.[*].sits.[*].type.capacity", containsInAnyOrder(sitType.getCapacity())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].halls.[*].sits.[*].type.description", containsInAnyOrder(sitType.getDescription())));
    }

    @Test
    public void getAllCinemasWithServiceExceptionTest() throws Exception {
        Mockito.when(cinemasService.getAllCinemas()).thenThrow(RuntimeException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/cinemas"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getCinemaTest() throws Exception {
        Mockito.when(cinemasService.getCinema(1L)).thenReturn(cinemaDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/cinemas/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(cinema.getId().intValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(cinema.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(cinema.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.halls.[*].id", containsInAnyOrder(hall.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.halls.[*].name", containsInAnyOrder(hall.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.halls.[*].size", containsInAnyOrder(hall.getSize())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.halls.[*].rows", containsInAnyOrder(hall.getRows())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.halls.[*].description", containsInAnyOrder(hall.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.halls.[*].placesInRow", containsInAnyOrder(hall.getPlacesInRow())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.halls.[*].sits.[*].id", containsInAnyOrder(sit.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.halls.[*].sits.[*].row", containsInAnyOrder(sit.getRow())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.halls.[*].sits.[*].place", containsInAnyOrder(sit.getPlace())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.halls.[*].sits.[*].type.id", containsInAnyOrder(sitType.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.halls.[*].sits.[*].type.name", containsInAnyOrder(sitType.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.halls.[*].sits.[*].type.capacity", containsInAnyOrder(sitType.getCapacity())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.halls.[*].sits.[*].type.description", containsInAnyOrder(sitType.getDescription())));
    }

    @Test
    public void getNotExistedCinemaTest() throws Exception {
        Mockito.when(cinemasService.getCinema(1L)).thenThrow(CinemaNotFoundException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/cinemas/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

    @Test
    public void getCinemaWithServiceExceptionTest() throws Exception {
        Mockito.when(cinemasService.getCinema(1L)).thenThrow(RuntimeException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/cinemas/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createCinemaTest() throws Exception {
        Mockito.when(cinemasService.createCinema(cinema)).thenReturn(cinemaDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/cinemas")
                        .flashAttr("cinema", cinema))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(cinema.getId().intValue()));
    }

    @Test
    public void createAlreadyExistedCinemaTest() throws Exception {
        Mockito.when(cinemasService.createCinema(cinema)).thenThrow(CinemaAlreadyExistException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/cinemas")
                        .flashAttr("cinema", cinema))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createCinemaWithServiceExceptionTest() throws Exception {
        Mockito.when(cinemasService.createCinema(Mockito.any(Cinema.class))).thenThrow(CinemaCreationException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/cinemas")
                        .flashAttr("cinema", cinema))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateCinemaTest() throws Exception {
        Mockito.when(cinemasService.updateCinema(cinema)).thenReturn(cinemaDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/cinemas")
                        .flashAttr("cinema", cinema))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(cinema.getId().intValue()));
    }

    @Test
    public void updateNotExistedCinemaTest() throws Exception {
        Mockito.when(cinemasService.updateCinema(cinema)).thenThrow(CinemaNotFoundException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/cinemas")
                        .flashAttr("cinema", cinema))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateCinemaWithServiceExceptionTest() throws Exception {
        Mockito.when(cinemasService.updateCinema(Mockito.any(Cinema.class))).thenThrow(CinemaUpdatingException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/cinemas")
                        .flashAttr("cinema", cinema))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteCinemaTest() throws Exception {
        Mockito.when(cinemasService.deleteCinemasByIds(Arrays.asList(1L, 3L, 5L))).thenReturn(Arrays.asList(1L, 5L));

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/cinemas")
                        .param("ids", "1,3,5"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]", containsInAnyOrder(1, 5)));
    }

    @Test
    public void deleteCinemasWithServiceExceptionTest() throws Exception {
        Mockito.when(cinemasService.deleteCinemasByIds(Arrays.asList(1L, 3L, 5L))).thenThrow(CinemaDeletingException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/cinemas")
                        .param("ids", "1,3,5"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void addHallToTheCinemaTest() throws Exception {
        Mockito.when(cinemasService.addHall(1L, 1L, hall)).thenReturn(hallDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/cinemas/1")
                        .flashAttr("hall", hall)
                        .param("sitsTypeId", "1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(hall.getId().intValue()));

    }

    @Test
    public void addHallToTheCinemaWithNotExistedCinemaTest() throws Exception {
        Mockito.when(cinemasService.addHall(Mockito.anyLong(), Mockito.same(1L), Mockito.same(hall))).thenThrow(CinemaNotFoundException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/cinemas/1")
                        .flashAttr("hall", hall)
                        .param("sitsTypeId", "1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void addHallToTheCinemaWithNotExistedSitTypeTest() throws Exception {
        Mockito.when(cinemasService.addHall(Mockito.same(1L), Mockito.anyLong(), Mockito.same(hall))).thenThrow(SitTypeNotFoundException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/cinemas/1")
                        .flashAttr("hall", hall)
                        .param("sitsTypeId", "1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    public void addHallToTheCinemaWithServiceExceptionTest() throws Exception {
        Mockito.when(cinemasService.addHall(Mockito.anyLong(), Mockito.anyLong(), Mockito.any(Hall.class))).thenThrow(HallCreationException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/cinemas/1")
                        .flashAttr("hall", hall)
                        .param("sitsTypeId", "1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

}
