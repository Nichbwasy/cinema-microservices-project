package com.cinema.films.microservice.controllers;

import com.cinema.films.microservice.controllers.configs.FilmsControllersTestConfiguration;
import com.cinema.films.microservice.domains.GenreDto;
import com.cinema.films.microservice.exceptions.services.genres.*;
import com.cinema.films.microservice.models.Genre;
import com.cinema.films.microservice.services.FilmsService;
import com.cinema.films.microservice.services.GenresService;
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
import java.util.Collections;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {GenresController.class})
@Import(FilmsControllersTestConfiguration.class)
@AutoConfigureMockMvc( addFilters = false ) // disable security
public class GenresControllerTests {

    private static final Genre genre = new Genre(1L, "genre_name");
    private static final GenreDto genreDto = new GenreDto(1L, "genre_name");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenresService genresService;

    @MockBean
    private FilmsController filmsController;

    @MockBean
    private FilmsService filmsService;

    @Test
    public void getAllGenresTest() throws Exception {
        Mockito.when(genresService.getAllGenres()).thenReturn(Collections.singletonList(genreDto));

        this.mockMvc
                .perform(MockMvcRequestBuilders
                    .get("/films/genres"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id", containsInAnyOrder(genreDto.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name", containsInAnyOrder(genreDto.getName())));
    }

    @Test
    public void getAllGenresWithServiceExceptionTest() throws Exception {
        Mockito.when(genresService.getAllGenres()).thenThrow(RuntimeException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/films/genres"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getGenreTest() throws Exception {
        Mockito.when(genresService.getGenre(1L)).thenReturn(genreDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/films/genres/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(genreDto.getId().intValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(genreDto.getName()));
    }

    @Test
    public void getNotExistedGenreTest() throws Exception {
        Mockito.when(genresService.getGenre(Mockito.anyLong())).thenThrow(GenreNotFoundException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/films/genres/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getGenreWithServiceExceptionTest() throws Exception {
        Mockito.when(genresService.getGenre(Mockito.anyLong())).thenThrow(RuntimeException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/films/genres/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createGenreTest() throws Exception {
        Mockito.when(genresService.createGenre(genre)).thenReturn(genreDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/films/genres")
                        .flashAttr("genre", genre))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(1L));
    }

    @Test
    public void createAlreadyExistedGenreTest() throws Exception {
        Mockito.when(genresService.createGenre(genre)).thenThrow(GenreAlreadyExistException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/films/genres")
                        .flashAttr("genre", genre))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createGenreWithServiceExceptionTest() throws Exception {
        Mockito.when(genresService.createGenre(genre)).thenThrow(GenreCreationException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/films/genres")
                        .flashAttr("genre", genre))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateGenreTest() throws Exception {
        Mockito.when(genresService.updateGenre(genre)).thenReturn(genreDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/films/genres")
                        .flashAttr("genre", genre))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(1L));
    }

    @Test
    public void updateNotExistedGenreTest() throws Exception {
        Mockito.when(genresService.updateGenre(genre)).thenThrow(GenreNotFoundException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/films/genres")
                        .flashAttr("genre", genre))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateGenreWithServiceExceptionTest() throws Exception {
        Mockito.when(genresService.updateGenre(genre)).thenThrow(GenreUpdatingException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/films/genres")
                        .flashAttr("genre", genre))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteGenreTest() throws Exception {
        Mockito.when(genresService.deleteGenre(1L)).thenReturn(1L);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/films/genres/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(1L));
    }

    @Test
    public void deleteNotExistedGenreTest() throws Exception {
        Mockito.when(genresService.deleteGenre(Mockito.anyLong())).thenThrow(GenreNotFoundException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/films/genres/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteGenreWithServiceExceptionTest() throws Exception {
        Mockito.when(genresService.deleteGenre(Mockito.anyLong())).thenThrow(GenreDeletingException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/films/genres/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

}
