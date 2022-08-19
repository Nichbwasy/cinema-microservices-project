package com.cinema.cinemas.microservice.controllers;

import com.cinema.cinemas.microservice.controllers.configs.ControllersTestConfiguration;
import com.cinema.cinemas.microservice.domains.CinemaDto;
import com.cinema.cinemas.microservice.domains.HallDto;
import com.cinema.cinemas.microservice.domains.SitDto;
import com.cinema.cinemas.microservice.domains.SitTypeDto;
import com.cinema.cinemas.microservice.exceptions.services.cinemas.CinemaNotFoundException;
import com.cinema.cinemas.microservice.exceptions.services.halls.HallCreationException;
import com.cinema.cinemas.microservice.exceptions.services.halls.HallDeletingException;
import com.cinema.cinemas.microservice.exceptions.services.halls.HallNotFoundException;
import com.cinema.cinemas.microservice.exceptions.services.halls.HallUpdatingException;
import com.cinema.cinemas.microservice.exceptions.services.sits.types.SitTypeNotFoundException;
import com.cinema.cinemas.microservice.models.Cinema;
import com.cinema.cinemas.microservice.models.Hall;
import com.cinema.cinemas.microservice.models.Sit;
import com.cinema.cinemas.microservice.models.SitType;
import com.cinema.cinemas.microservice.services.HallsService;
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

import java.util.Collections;

import static org.hamcrest.Matchers.containsInAnyOrder;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {HallsController.class})
@Import(ControllersTestConfiguration.class)
@AutoConfigureMockMvc( addFilters = false ) // disable security
public class HallsControllerTests {

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
    private HallsService hallsService;

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
    public void getAllCinemaHallsTest() throws Exception {
        Mockito.when(hallsService.getAllHallsInCinema(1L)).thenReturn(Collections.singletonList(hallDto));

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/cinemas/1/halls"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id", containsInAnyOrder(hall.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name", containsInAnyOrder(hall.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].size", containsInAnyOrder(hall.getSize())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].rows", containsInAnyOrder(hall.getRows())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].description", containsInAnyOrder(hall.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].placesInRow", containsInAnyOrder(hall.getPlacesInRow())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].sits.[*].id", containsInAnyOrder(sit.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].sits.[*].row", containsInAnyOrder(sit.getRow())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].sits.[*].place", containsInAnyOrder(sit.getPlace())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].sits.[*].type.id", containsInAnyOrder(sitType.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].sits.[*].type.name", containsInAnyOrder(sitType.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].sits.[*].type.capacity", containsInAnyOrder(sitType.getCapacity())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].sits.[*].type.description", containsInAnyOrder(sitType.getDescription())));
    }

    @Test
    public void getAllCinemaHallsWithServiceExceptionTest() throws Exception {
        Mockito.when(hallsService.getAllHallsInCinema(Mockito.anyLong())).thenThrow(RuntimeException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/cinemas/1/halls"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getHallTest() throws Exception {
        Mockito.when(hallsService.getCinemaHall(1L, 1L)).thenReturn(hallDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/cinemas/1/halls/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(hall.getId().intValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(hall.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size").value(hall.getSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rows").value(hall.getRows()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(hall.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.placesInRow").value(hall.getPlacesInRow()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sits.[*].id", containsInAnyOrder(sit.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sits.[*].row", containsInAnyOrder(sit.getRow())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sits.[*].place", containsInAnyOrder(sit.getPlace())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sits.[*].type.id", containsInAnyOrder(sitType.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sits.[*].type.name", containsInAnyOrder(sitType.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sits.[*].type.capacity", containsInAnyOrder(sitType.getCapacity())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sits.[*].type.description", containsInAnyOrder(sitType.getDescription())));
    }

    @Test
    public void getHallWhenHallNotFoundOrNotBelongCinemaTest() throws Exception {
        Mockito.when(hallsService.getCinemaHall(Mockito.same(1L), Mockito.anyLong())).thenThrow(HallNotFoundException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/cinemas/1/halls/2"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getHallWhenCinemaNotExistTest() throws Exception {
        Mockito.when(hallsService.getCinemaHall(Mockito.anyLong(), Mockito.anyLong())).thenThrow(CinemaNotFoundException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                                .get("/cinemas/2/halls/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getHallWithServiceExceptionTest() throws Exception {
        Mockito.when(hallsService.getCinemaHall(Mockito.anyLong(), Mockito.anyLong())).thenThrow(RuntimeException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                                .get("/cinemas/2/halls/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createHallTest() throws Exception {
        Mockito.when(hallsService.createHall(1L, hall)).thenReturn(hallDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/cinemas/1/halls")
                        .flashAttr("hall", hall)
                        .param("sitTypeId", "1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(hall.getId().intValue()));
    }

    @Test
    public void createHallWithNotExistedSitTypeIdTest() throws Exception {
        Mockito.when(hallsService.createHall(Mockito.anyLong(), Mockito.same(hall))).thenThrow(SitTypeNotFoundException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/cinemas/1/halls")
                        .flashAttr("hall", hall)
                        .param("sitTypeId", "3"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createHallWithServiceExceptionTest() throws Exception {
        Mockito.when(hallsService.createHall(Mockito.anyLong(), Mockito.any(Hall.class))).thenThrow(HallCreationException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/cinemas/1/halls")
                        .flashAttr("hall", hall)
                        .param("sitTypeId", "1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateHallTest() throws Exception {
        Mockito.when(hallsService.updateHall(hall)).thenReturn(hallDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/cinemas/1/halls")
                        .flashAttr("hall", hall))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(hall.getId().intValue()));
    }

    @Test
    public void updateNotExistedHallTest() throws Exception {
        Mockito.when(hallsService.updateHall(hall)).thenThrow(HallNotFoundException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/cinemas/1/halls")
                        .flashAttr("hall", hall))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateHallWithServiceExceptionTest() throws Exception {
        Mockito.when(hallsService.updateHall(Mockito.any(Hall.class))).thenThrow(HallUpdatingException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/cinemas/1/halls")
                        .flashAttr("hall", hall))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteHallTest() throws Exception {
        Mockito.when(hallsService.deleteHallFromCinema(1L, 1L)).thenReturn(1L);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/cinemas/1/halls/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(hall.getId().intValue()));
    }

    @Test
    public void deleteHallInNotExistedCinemaTest() throws Exception {
        Mockito.when(hallsService.deleteHallFromCinema(Mockito.anyLong(), Mockito.anyLong())).thenThrow(CinemaNotFoundException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/cinemas/1/halls/3"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteNotExistedHallTest() throws Exception {
        Mockito.when(hallsService.deleteHallFromCinema(Mockito.same(1L), Mockito.anyLong())).thenThrow(HallNotFoundException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/cinemas/1/halls/3"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteHallWithServiceExceptionTest() throws Exception {
        Mockito.when(hallsService.deleteHallFromCinema(Mockito.anyLong(), Mockito.anyLong())).thenThrow(HallDeletingException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/cinemas/1/halls/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

}
