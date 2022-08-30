package com.cinema.films.microservice.controllers;

import com.cinema.films.microservice.controllers.configs.FilmsControllersTestConfiguration;
import com.cinema.films.microservice.controllers.dtos.FilmFormDto;
import com.cinema.films.microservice.domains.DirectorDto;
import com.cinema.films.microservice.domains.FilmDto;
import com.cinema.films.microservice.domains.FilmImgResourceDto;
import com.cinema.films.microservice.domains.GenreDto;
import com.cinema.films.microservice.exceptions.services.directors.DirectorNotFoundException;
import com.cinema.films.microservice.exceptions.services.films.FilmAlreadyExistException;
import com.cinema.films.microservice.exceptions.services.films.FilmCreationException;
import com.cinema.films.microservice.exceptions.services.films.FilmNotFoundException;
import com.cinema.films.microservice.exceptions.services.films.FilmUpdatingException;
import com.cinema.films.microservice.exceptions.services.genres.GenreNotFoundException;
import com.cinema.films.microservice.models.Director;
import com.cinema.films.microservice.models.Film;
import com.cinema.films.microservice.models.FilmImgResource;
import com.cinema.films.microservice.models.Genre;
import com.cinema.films.microservice.services.DirectorsService;
import com.cinema.films.microservice.services.FilmsService;
import com.cinema.films.microservice.services.GenresService;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Collections;
import java.util.Date;

import static org.hamcrest.Matchers.containsInAnyOrder;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {FilmsController.class})
@Import(FilmsControllersTestConfiguration.class)
@AutoConfigureMockMvc( addFilters = false ) // disable security
public class FilmsControllersTests {

    private static final Genre genre = new Genre(1L, "genre_name");
    private static final GenreDto genreDto = new GenreDto(1L, "genre_name");
    private final static Director director = new Director(1L, "first_name", "last_name");
    private final static DirectorDto directorDto = new DirectorDto(1L, "first_name", "last_name");
    private static final Film film = new Film(1L, "film_name", "description", 2001, null, null, null);
    private static final FilmDto filmDto = new FilmDto(1L, "film_name", "description", 2001, null, null);
    private static final FilmImgResource filmImgResource = new FilmImgResource(1L, "IMG.png", "png", 100, 100, 10000L, new Date(), new Date());
    private static final FilmImgResourceDto filmImgResourceDto = new FilmImgResourceDto(1L, "IMG.png", "png", 100, 100, 10000L, new Date(), new Date());

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilmsService filmsService;

    @MockBean
    private DirectorsService directorsService;

    @MockBean
    private GenresService genresService;

    @BeforeAll
    private static void init() {
        film.setGenre(genre);
        film.setDirector(director);
        film.setImgResource(filmImgResource);
        filmDto.setGenre(genreDto);
        filmDto.setDirector(directorDto);
    }

    @Test
    public void getAllFilmsTest() throws Exception {
        Mockito.when(filmsService.getAllFilms()).thenReturn(Collections.singletonList(filmDto));

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/films"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id", containsInAnyOrder(filmDto.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name", containsInAnyOrder(filmDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].year", containsInAnyOrder(filmDto.getYear())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].description", containsInAnyOrder(filmDto.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].genre.id", containsInAnyOrder(filmDto.getGenre().getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].genre.name", containsInAnyOrder(filmDto.getGenre().getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].director.id", containsInAnyOrder(filmDto.getDirector().getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].director.firstName", containsInAnyOrder(filmDto.getDirector().getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].director.lastName", containsInAnyOrder(filmDto.getDirector().getLastName())));
    }

    @Test
    public void getAllFilmsWithServiceExceptionTest() throws Exception {
        Mockito.when(filmsService.getAllFilms()).thenThrow(RuntimeException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/films"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getFilmTest() throws Exception {
        Mockito.when(filmsService.getFilm(1L)).thenReturn(filmDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/films/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(filmDto.getId().intValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(filmDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.year").value(filmDto.getYear()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(filmDto.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre.id").value(filmDto.getGenre().getId().intValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre.name").value(filmDto.getGenre().getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.director.id").value(filmDto.getDirector().getId().intValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.director.firstName").value(filmDto.getDirector().getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.director.lastName").value(filmDto.getDirector().getLastName()));
    }

    @Test
    public void getNotExistedFilmTest() throws Exception {
        Mockito.when(filmsService.getFilm(Mockito.anyLong())).thenThrow(FilmNotFoundException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/films/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getFilmWithServiceExceptionTest() throws Exception {
        Mockito.when(filmsService.getFilm(Mockito.anyLong())).thenThrow(RuntimeException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/films/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getFilmsPage() throws Exception {
        Mockito.when(filmsService.getPageOfFilms(0)).thenReturn(Collections.singletonList(filmDto));

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/films/catalog/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id", containsInAnyOrder(filmDto.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name", containsInAnyOrder(filmDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].year", containsInAnyOrder(filmDto.getYear())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].description", containsInAnyOrder(filmDto.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].genre.id", containsInAnyOrder(filmDto.getGenre().getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].genre.name", containsInAnyOrder(filmDto.getGenre().getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].director.id", containsInAnyOrder(filmDto.getDirector().getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].director.firstName", containsInAnyOrder(filmDto.getDirector().getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].director.lastName", containsInAnyOrder(filmDto.getDirector().getLastName())));
    }

    @Test
    public void getFilmsWithServiceExceptionPage() throws Exception {
        Mockito.when(filmsService.getPageOfFilms(Mockito.anyInt())).thenThrow(RuntimeException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/films/catalog/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createFilmTest() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream testPoster = classLoader.getResourceAsStream("tests/poster.jpg");
        MultipartFile multipartFile = new MockMultipartFile("poster.jpg", testPoster);
        FilmFormDto filmFormDto = new FilmFormDto(1L, "film_name", "description", 2001, 1L, 1L);

        Mockito.when(filmsService.saveFilm(Mockito.any(Film.class), Mockito.any())).thenReturn(filmDto);
        Mockito.when(directorsService.getDirector(1L)).thenReturn(directorDto);
        Mockito.when(genresService.getGenre(1L)).thenReturn(genreDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/films")
                        .flashAttr("filmFormDto", filmFormDto)
                        .flashAttr("img", multipartFile))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(1));
    }

    @Test
    public void createAlreadyExistedFilmTest() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream testPoster = classLoader.getResourceAsStream("tests/poster.jpg");
        MultipartFile multipartFile = new MockMultipartFile("poster.jpg", testPoster);
        FilmFormDto filmFormDto = new FilmFormDto(1L, "film_name", "description", 2001, 1L, 1L);

        Mockito.when(filmsService.saveFilm(Mockito.any(Film.class), Mockito.any())).thenThrow(FilmAlreadyExistException.class);
        Mockito.when(directorsService.getDirector(1L)).thenReturn(directorDto);
        Mockito.when(genresService.getGenre(1L)).thenReturn(genreDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/films")
                        .flashAttr("filmFormDto", filmFormDto)
                        .flashAttr("img", multipartFile))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createFilmWithNullDataTest() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream testPoster = classLoader.getResourceAsStream("tests/poster.jpg");
        MultipartFile multipartFile = new MockMultipartFile("poster.jpg", testPoster);

        Mockito.when(filmsService.saveFilm(Mockito.nullable(Film.class), Mockito.any())).thenThrow(FilmCreationException.class);
        Mockito.when(directorsService.getDirector(1L)).thenReturn(directorDto);
        Mockito.when(genresService.getGenre(1L)).thenReturn(genreDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/films")
                        .flashAttr("img", multipartFile))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createFilmWithNullImgDataTest() throws Exception {
        Mockito.when(filmsService.saveFilm(Mockito.any(Film.class), Mockito.nullable(MultipartFile.class))).thenThrow(FilmCreationException.class);
        Mockito.when(directorsService.getDirector(1L)).thenReturn(directorDto);
        Mockito.when(genresService.getGenre(1L)).thenReturn(genreDto);
        FilmFormDto filmFormDto = new FilmFormDto(1L, "film_name", "description", 2001, 1L, 1L);


        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/films")
                        .flashAttr("filmFormDto", filmFormDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createFilmWithServiceExceptionTest() throws Exception {
        Mockito.when(filmsService.saveFilm(Mockito.any(Film.class), Mockito.any())).thenThrow(FilmCreationException.class);
        Mockito.when(directorsService.getDirector(1L)).thenReturn(directorDto);
        Mockito.when(genresService.getGenre(1L)).thenReturn(genreDto);
        FilmFormDto filmFormDto = new FilmFormDto(1L, "film_name", "description", 2001, 1L, 1L);


        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/films")
                        .flashAttr("filmFormDto", filmFormDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createFilmWithNotExistedDirectorTest() throws Exception {
        Mockito.when(filmsService.saveFilm(Mockito.any(Film.class), Mockito.any())).thenReturn(filmDto);
        Mockito.when(directorsService.getDirector(Mockito.anyLong())).thenThrow(DirectorNotFoundException.class);
        Mockito.when(genresService.getGenre(1L)).thenReturn(genreDto);
        FilmFormDto filmFormDto = new FilmFormDto(1L, "film_name", "description", 2001, 1L, 1L);


        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/films")
                        .flashAttr("filmFormDto", filmFormDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createFilmWithNotExistedGenreTest() throws Exception {
        Mockito.when(filmsService.saveFilm(Mockito.any(Film.class), Mockito.any())).thenReturn(filmDto);
        Mockito.when(directorsService.getDirector(1L)).thenReturn(directorDto);
        Mockito.when(genresService.getGenre(Mockito.anyLong())).thenThrow(GenreNotFoundException.class);
        FilmFormDto filmFormDto = new FilmFormDto(1L, "film_name", "description", 2001, 1L, 1L);


        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/films")
                        .flashAttr("filmFormDto", filmFormDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createFilmWithDirectorsServiceExceptionTest() throws Exception {
        Mockito.when(filmsService.saveFilm(Mockito.any(Film.class), Mockito.any())).thenReturn(filmDto);
        Mockito.when(directorsService.getDirector(Mockito.anyLong())).thenThrow(RuntimeException.class);
        Mockito.when(genresService.getGenre(1L)).thenReturn(genreDto);
        FilmFormDto filmFormDto = new FilmFormDto(1L, "film_name", "description", 2001, 1L, 1L);


        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/films")
                        .flashAttr("filmFormDto", filmFormDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createFilmGenresServiceExceptionTest() throws Exception {
        Mockito.when(filmsService.saveFilm(Mockito.any(Film.class), Mockito.any())).thenReturn(filmDto);
        Mockito.when(directorsService.getDirector(1L)).thenReturn(directorDto);
        Mockito.when(genresService.getGenre(Mockito.anyLong())).thenThrow(RuntimeException.class);
        FilmFormDto filmFormDto = new FilmFormDto(1L, "film_name", "description", 2001, 1L, 1L);


        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/films")
                        .flashAttr("filmFormDto", filmFormDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    public void updateFilmTest() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream testPoster = classLoader.getResourceAsStream("tests/poster.jpg");
        MultipartFile multipartFile = new MockMultipartFile("poster.jpg", testPoster);
        FilmFormDto filmFormDto = new FilmFormDto(1L, "film_name", "description", 2001, 1L, 1L);

        Mockito.when(filmsService.updateFilm(Mockito.any(Film.class), Mockito.any())).thenReturn(filmDto);
        Mockito.when(directorsService.getDirector(1L)).thenReturn(directorDto);
        Mockito.when(genresService.getGenre(1L)).thenReturn(genreDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/films")
                        .flashAttr("filmFormDto", filmFormDto)
                        .flashAttr("img", multipartFile))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(1));
    }

    @Test
    public void updateAlreadyExistedFilmTest() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream testPoster = classLoader.getResourceAsStream("tests/poster.jpg");
        MultipartFile multipartFile = new MockMultipartFile("poster.jpg", testPoster);
        FilmFormDto filmFormDto = new FilmFormDto(1L, "film_name", "description", 2001, 1L, 1L);

        Mockito.when(filmsService.updateFilm(Mockito.any(Film.class), Mockito.any())).thenThrow(FilmAlreadyExistException.class);
        Mockito.when(directorsService.getDirector(1L)).thenReturn(directorDto);
        Mockito.when(genresService.getGenre(1L)).thenReturn(genreDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/films")
                        .flashAttr("filmFormDto", filmFormDto)
                        .flashAttr("img", multipartFile))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateFilmWithNullDataTest() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream testPoster = classLoader.getResourceAsStream("tests/poster.jpg");
        MultipartFile multipartFile = new MockMultipartFile("poster.jpg", testPoster);

        Mockito.when(filmsService.updateFilm(Mockito.nullable(Film.class), Mockito.any())).thenThrow(FilmUpdatingException.class);
        Mockito.when(directorsService.getDirector(1L)).thenReturn(directorDto);
        Mockito.when(genresService.getGenre(1L)).thenReturn(genreDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/films")
                        .flashAttr("img", multipartFile))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateFilmWithNullImgDataTest() throws Exception {
        Mockito.when(filmsService.updateFilm(Mockito.any(Film.class), Mockito.nullable(MultipartFile.class))).thenThrow(FilmUpdatingException.class);
        Mockito.when(directorsService.getDirector(1L)).thenReturn(directorDto);
        Mockito.when(genresService.getGenre(1L)).thenReturn(genreDto);
        FilmFormDto filmFormDto = new FilmFormDto(1L, "film_name", "description", 2001, 1L, 1L);


        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/films")
                        .flashAttr("filmFormDto", filmFormDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateFilmWithServiceExceptionTest() throws Exception {
        Mockito.when(filmsService.updateFilm(Mockito.any(Film.class), Mockito.any())).thenThrow(FilmUpdatingException.class);
        Mockito.when(directorsService.getDirector(1L)).thenReturn(directorDto);
        Mockito.when(genresService.getGenre(1L)).thenReturn(genreDto);
        FilmFormDto filmFormDto = new FilmFormDto(1L, "film_name", "description", 2001, 1L, 1L);


        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/films")
                        .flashAttr("filmFormDto", filmFormDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateFilmWithNotExistedDirectorTest() throws Exception {
        Mockito.when(filmsService.updateFilm(Mockito.any(Film.class), Mockito.any())).thenReturn(filmDto);
        Mockito.when(directorsService.getDirector(Mockito.anyLong())).thenThrow(DirectorNotFoundException.class);
        Mockito.when(genresService.getGenre(1L)).thenReturn(genreDto);
        FilmFormDto filmFormDto = new FilmFormDto(1L, "film_name", "description", 2001, 1L, 1L);


        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/films")
                        .flashAttr("filmFormDto", filmFormDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateFilmWithNotExistedGenreTest() throws Exception {
        Mockito.when(filmsService.updateFilm(Mockito.any(Film.class), Mockito.any())).thenReturn(filmDto);
        Mockito.when(directorsService.getDirector(1L)).thenReturn(directorDto);
        Mockito.when(genresService.getGenre(Mockito.anyLong())).thenThrow(GenreNotFoundException.class);
        FilmFormDto filmFormDto = new FilmFormDto(1L, "film_name", "description", 2001, 1L, 1L);


        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/films")
                        .flashAttr("filmFormDto", filmFormDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateFilmWithDirectorsServiceExceptionTest() throws Exception {
        Mockito.when(filmsService.updateFilm(Mockito.any(Film.class), Mockito.any())).thenReturn(filmDto);
        Mockito.when(directorsService.getDirector(Mockito.anyLong())).thenThrow(RuntimeException.class);
        Mockito.when(genresService.getGenre(1L)).thenReturn(genreDto);
        FilmFormDto filmFormDto = new FilmFormDto(1L, "film_name", "description", 2001, 1L, 1L);


        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/films")
                        .flashAttr("filmFormDto", filmFormDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateFilmGenresServiceExceptionTest() throws Exception {
        Mockito.when(filmsService.updateFilm(Mockito.any(Film.class), Mockito.any())).thenReturn(filmDto);
        Mockito.when(directorsService.getDirector(1L)).thenReturn(directorDto);
        Mockito.when(genresService.getGenre(Mockito.anyLong())).thenThrow(RuntimeException.class);
        FilmFormDto filmFormDto = new FilmFormDto(1L, "film_name", "description", 2001, 1L, 1L);


        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/films")
                        .flashAttr("filmFormDto", filmFormDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    public void deleteFilmTest() throws Exception {
        Mockito.when(filmsService.deleteFilm(1L)).thenReturn(1L);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/films/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(1));
    }

    @Test
    public void deleteNotExistedFilmTest() throws Exception {
        Mockito.when(filmsService.deleteFilm(Mockito.anyLong())).thenThrow(FilmNotFoundException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/films/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteFilmWithServiceExceptionTest() throws Exception {
        Mockito.when(filmsService.deleteFilm(Mockito.anyLong())).thenThrow(FilmUpdatingException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/films/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getFilmImgMetadataTest() throws Exception {
        Mockito.when(filmsService.getFilmPosterMetadata(1L)).thenReturn(filmImgResourceDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/films/1/poster/metadata"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(filmImgResourceDto.getId().intValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fileName").value(filmImgResourceDto.getFileName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fileExtension").value(filmImgResourceDto.getFileExtension()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.resolutionX").value(filmImgResourceDto.getResolutionX()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.resolutionY").value(filmImgResourceDto.getResolutionY()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size").value(filmImgResourceDto.getSize()));
    }

    @Test
    public void getNotExistedFilmImgMetadataTest() throws Exception {
        Mockito.when(filmsService.getFilmPosterMetadata(Mockito.anyLong())).thenThrow(FilmNotFoundException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/films/1/poster/metadata"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getFilmImgMetadataWithServiceExceptionTest() throws Exception {
        Mockito.when(filmsService.getFilmPosterMetadata(Mockito.anyLong())).thenThrow(RuntimeException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/films/1/poster/metadata"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

}
