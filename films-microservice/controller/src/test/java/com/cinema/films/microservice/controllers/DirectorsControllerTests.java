package com.cinema.films.microservice.controllers;

import com.cinema.films.microservice.controllers.configs.FilmsControllersTestConfiguration;
import com.cinema.films.microservice.domains.DirectorDto;
import com.cinema.films.microservice.domains.FilmDto;
import com.cinema.films.microservice.domains.FilmImgResourceDto;
import com.cinema.films.microservice.domains.GenreDto;
import com.cinema.films.microservice.exceptions.services.directors.*;
import com.cinema.films.microservice.models.Director;
import com.cinema.films.microservice.models.Film;
import com.cinema.films.microservice.models.FilmImgResource;
import com.cinema.films.microservice.models.Genre;
import com.cinema.films.microservice.services.DirectorsService;
import com.cinema.films.microservice.services.FilmsService;
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
@WebMvcTest(controllers = {DirectorsController.class})
@Import(FilmsControllersTestConfiguration.class)
@AutoConfigureMockMvc( addFilters = false ) // disable security
public class DirectorsControllerTests {

    private final static Director director = new Director(1L, "first_name", "last_name");
    private final static DirectorDto directorDto = new DirectorDto(1L, "first_name", "last_name");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DirectorsService directorsService;

    @MockBean
    private FilmsController filmsController;

    @MockBean
    private FilmsService filmsService;

    @Test
    public void getAllDirectorsTest() throws Exception {
        Mockito.when(directorsService.getAllDirectors()).thenReturn(Collections.singletonList(directorDto));

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/films/directors"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id", containsInAnyOrder(directorDto.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].firstName", containsInAnyOrder(directorDto.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].lastName", containsInAnyOrder(directorDto.getLastName())));
    }

    @Test
    public void getAllDirectorsWithServiceExceptionTest() throws Exception {
        Mockito.when(directorsService.getAllDirectors()).thenThrow(RuntimeException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/films/directors"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getDirectorTest() throws Exception {
        Mockito.when(directorsService.getDirector(1L)).thenReturn(directorDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/films/directors/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(directorDto.getId().intValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(directorDto.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(directorDto.getLastName()));
    }

    @Test
    public void getNotExistedDirectorTest() throws Exception {
        Mockito.when(directorsService.getDirector(1L)).thenThrow(DirectorNotFoundException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/films/directors/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getDirectorWithServiceExceptionTest() throws Exception {
        Mockito.when(directorsService.getDirector(Mockito.anyLong())).thenThrow(RuntimeException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/films/directors/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createDirectorTest() throws Exception {
        Mockito.when(directorsService.createDirector(director)).thenReturn(directorDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/films/directors")
                        .flashAttr("director", director))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(1));
    }

    @Test
    public void createAlreadyExistedDirectorTest() throws Exception {
        Mockito.when(directorsService.createDirector(director)).thenThrow(DirectorAlreadyExistException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/films/directors")
                        .flashAttr("director", director))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createDirectorWithServiceExceptionTest() throws Exception {
        Mockito.when(directorsService.createDirector(director)).thenThrow(DirectorCreationException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/films/directors")
                        .flashAttr("director", director))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateDirectorTest() throws Exception {
        Mockito.when(directorsService.updateDirector(director)).thenReturn(directorDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/films/directors")
                        .flashAttr("director", director))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(1));
    }

    @Test
    public void updateNotExistedDirectorTest() throws Exception {
        Mockito.when(directorsService.updateDirector(director)).thenThrow(DirectorNotFoundException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/films/directors")
                        .flashAttr("director", director))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateDirectorWithServiceExceptionTest() throws Exception {
        Mockito.when(directorsService.updateDirector(director)).thenThrow(DirectorUpdatingException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/films/directors")
                        .flashAttr("director", director))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteDirectorTest() throws Exception {
        Mockito.when(directorsService.deleteDirector(1L)).thenReturn(1L);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/films/directors/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(1));
    }

    @Test
    public void deleteNotExistedDirectorTest() throws Exception {
        Mockito.when(directorsService.deleteDirector(1L)).thenThrow(DirectorNotFoundException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/films/directors/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteDirectorWithServiceExceptionTest() throws Exception {
        Mockito.when(directorsService.deleteDirector(1L)).thenThrow(DirectorDeletingException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/films/directors/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

}
