package com.cinema.films.microservice.services;

import com.cinema.films.microservice.domains.FilmDto;
import com.cinema.films.microservice.domains.FilmImgResourceDto;
import com.cinema.films.microservice.exceptions.services.films.*;
import com.cinema.films.microservice.models.Director;
import com.cinema.films.microservice.models.Film;
import com.cinema.films.microservice.models.FilmImgResource;
import com.cinema.films.microservice.models.Genre;
import com.cinema.films.microservice.repositories.FilmImgResourcesRepository;
import com.cinema.films.microservice.repositories.FilmsRepository;
import com.cinema.films.microservice.services.configs.FilmsServicesTestConfiguration;
import com.cinema.films.microservice.storages.ResourcesStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.List;


@SpringBootTest(classes = FilmsService.class)
@Import(FilmsServicesTestConfiguration.class)
@ExtendWith(SpringExtension.class)
public class FilmsServiceTests {

    private static final Genre genre = new Genre(1L, "genre_name");
    private static final Director director = new Director(1L, "first_name", "last_name");
    private static final Film film = new Film(1L, "film_name", "description", 2001, null, null, null);
    private static final FilmImgResource filmImgResource = new FilmImgResource(1L, "IMG.png", "png", 100, 100, 10000L, new Date(), new Date());

    @Autowired
    private FilmsService filmsService;

    @MockBean
    private FilmsRepository filmsRepository;

    @MockBean
    private ResourcesStorage resourcesStorage;

    @MockBean
    private FilmImgResourcesRepository filmImgResourcesRepository;

    @BeforeAll
    public static void init() {
        film.setGenre(genre);
        film.setDirector(director);
        film.setImgResource(filmImgResource);
    }

    @Test
    public void getAllFilmsTest() {
        Mockito.when(filmsRepository.findAll()).thenReturn(Collections.singletonList(film));

        List<FilmDto> result = filmsService.getAllFilms();
        Assertions.assertEquals(1L, result.size());
        Assertions.assertEquals(film.getId(), result.get(0).getId());
        Assertions.assertEquals(film.getName(), result.get(0).getName());
        Assertions.assertEquals(film.getYear(), result.get(0).getYear());
        Assertions.assertEquals(film.getDescription(), result.get(0).getDescription());
        Assertions.assertEquals(film.getGenre().getId(), result.get(0).getGenre().getId());
        Assertions.assertEquals(film.getGenre().getName(), result.get(0).getGenre().getName());
        Assertions.assertEquals(film.getDirector().getId(), result.get(0).getDirector().getId());
        Assertions.assertEquals(film.getDirector().getLastName(), result.get(0).getDirector().getLastName());
        Assertions.assertEquals(film.getDirector().getFirstName(), result.get(0).getDirector().getFirstName());
    }

    @Test
    public void getAllFilmsWithRepositoryExceptionTest() {
        Mockito.when(filmsRepository.findAll()).thenThrow(RuntimeException.class);

        Assertions.assertThrows(RuntimeException.class, () -> filmsService.getAllFilms());
    }

    @Test
    public void getPageOfFilmsTest() {
        Page<Film> filmPage = new PageImpl(Collections.singletonList(film));
        Mockito.when(filmsRepository.findAll(Mockito.any(Pageable.class))).thenReturn(filmPage);

        List<FilmDto> result = filmsService.getPageOfFilms(1);
        Assertions.assertEquals(1L, result.size());
        Assertions.assertEquals(film.getId(), result.get(0).getId());
        Assertions.assertEquals(film.getName(), result.get(0).getName());
        Assertions.assertEquals(film.getYear(), result.get(0).getYear());
        Assertions.assertEquals(film.getDescription(), result.get(0).getDescription());
        Assertions.assertEquals(film.getGenre().getId(), result.get(0).getGenre().getId());
        Assertions.assertEquals(film.getGenre().getName(), result.get(0).getGenre().getName());
        Assertions.assertEquals(film.getDirector().getId(), result.get(0).getDirector().getId());
        Assertions.assertEquals(film.getDirector().getLastName(), result.get(0).getDirector().getLastName());
        Assertions.assertEquals(film.getDirector().getFirstName(), result.get(0).getDirector().getFirstName());
    }

    @Test
    public void getPageOfFilmsWithRepositoryExceptionTest() {
        Mockito.when(filmsRepository.findAll(Mockito.any(Pageable.class))).thenThrow(RuntimeException.class);

        Assertions.assertThrows(RuntimeException.class, () -> filmsService.getPageOfFilms(1));
    }

    @Test
    public void getFilmTest() {
        Mockito.when(filmsRepository.existsById(1L)).thenReturn(true);
        Mockito.when(filmsRepository.getReferenceById(1L)).thenReturn(film);

        FilmDto result = filmsService.getFilm(1L);
        Assertions.assertEquals(film.getId(), result.getId());
        Assertions.assertEquals(film.getName(), result.getName());
        Assertions.assertEquals(film.getYear(), result.getYear());
        Assertions.assertEquals(film.getDescription(), result.getDescription());
        Assertions.assertEquals(film.getGenre().getId(), result.getGenre().getId());
        Assertions.assertEquals(film.getGenre().getName(), result.getGenre().getName());
        Assertions.assertEquals(film.getDirector().getId(), result.getDirector().getId());
        Assertions.assertEquals(film.getDirector().getLastName(), result.getDirector().getLastName());
        Assertions.assertEquals(film.getDirector().getFirstName(), result.getDirector().getFirstName());
    }

    @Test
    public void getNotExistedFilmTest() {
        Mockito.when(filmsRepository.existsById(1L)).thenReturn(false);

        Assertions.assertThrows(FilmNotFoundException.class, () -> filmsService.getFilm(1L));
    }

    @Test
    public void getFilmWithRepositoryExceptionTest() {
        Mockito.when(filmsRepository.existsById(1L)).thenThrow(RuntimeException.class);

        Assertions.assertThrows(RuntimeException.class, () -> filmsService.getFilm(1L));
    }

    @Test
    public void saveFilmTest() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream testPoster = classLoader.getResourceAsStream("tests/poster.jpg");
        MultipartFile file = new MockMultipartFile("poster.jpg", testPoster);

        Mockito.when(filmsRepository.existsByName(film.getName())).thenReturn(false);
        Mockito.when(filmsRepository.save(film)).thenReturn(film);
        Mockito.when(resourcesStorage.saveFile(Mockito.anyString(), Mockito.any(InputStream.class), Mockito.anyLong(), Mockito.nullable(String.class))).thenReturn(true);
        Mockito.when(filmImgResourcesRepository.save(Mockito.any(FilmImgResource.class))).thenReturn(filmImgResource);

        FilmDto result = filmsService.saveFilm(film, file);
        Assertions.assertEquals(film.getId(), result.getId());
        Assertions.assertEquals(film.getName(), result.getName());
        Assertions.assertEquals(film.getYear(), result.getYear());
        Assertions.assertEquals(film.getDescription(), result.getDescription());
        Assertions.assertEquals(film.getGenre().getId(), result.getGenre().getId());
        Assertions.assertEquals(film.getGenre().getName(), result.getGenre().getName());
        Assertions.assertEquals(film.getDirector().getId(), result.getDirector().getId());
        Assertions.assertEquals(film.getDirector().getLastName(), result.getDirector().getLastName());
        Assertions.assertEquals(film.getDirector().getFirstName(), result.getDirector().getFirstName());
    }

    @Test
    public void saveFilmWithWrongFileExtensionTest() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream testPoster = classLoader.getResourceAsStream("tests/wrongExt.mp3");
        MultipartFile file = new MockMultipartFile("wrongExt.mp3", testPoster);

        Mockito.when(filmsRepository.existsByName(film.getName())).thenReturn(false);
        Mockito.when(filmsRepository.save(film)).thenReturn(film);
        Mockito.when(resourcesStorage.saveFile(Mockito.anyString(), Mockito.any(InputStream.class), Mockito.anyLong(), Mockito.nullable(String.class))).thenReturn(true);
        Mockito.when(filmImgResourcesRepository.save(Mockito.any(FilmImgResource.class))).thenReturn(filmImgResource);

        Assertions.assertThrows(FilmCreationException.class, () -> filmsService.saveFilm(film, file));
    }

    @Test
    public void saveAlreadyExistedFilmTest() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream testPoster = classLoader.getResourceAsStream("tests/wrongExt.mp3");
        MultipartFile file = new MockMultipartFile("wrongExt.mp3", testPoster);

        Mockito.when(filmsRepository.existsByName(film.getName())).thenReturn(true);

        Assertions.assertThrows(FilmAlreadyExistException.class, () -> filmsService.saveFilm(film, file));
    }

    @Test
    public void saveFilmWithResourceStorageExceptionTest() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream testPoster = classLoader.getResourceAsStream("tests/wrongExt.mp3");
        MultipartFile file = new MockMultipartFile("wrongExt.mp3", testPoster);

        Mockito.when(filmsRepository.existsByName(film.getName())).thenReturn(false);
        Mockito.when(filmsRepository.save(film)).thenReturn(film);
        Mockito.when(resourcesStorage.saveFile(Mockito.anyString(), Mockito.any(InputStream.class), Mockito.anyLong(), Mockito.nullable(String.class))).thenReturn(false);

        Assertions.assertThrows(FilmCreationException.class, () -> filmsService.saveFilm(film, file));
    }

    @Test
    public void saveFilmWithRepositoryExceptionTest() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream testPoster = classLoader.getResourceAsStream("tests/wrongExt.mp3");
        MultipartFile file = new MockMultipartFile("wrongExt.mp3", testPoster);

        Mockito.when(filmsRepository.existsByName(film.getName())).thenReturn(false);
        Mockito.when(filmsRepository.save(film)).thenThrow(RuntimeException.class);

        Assertions.assertThrows(FilmCreationException.class, () -> filmsService.saveFilm(film, file));
    }

    @Test
    public void saveFilmWithImgResourcesRepositoryExceptionTest() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream testPoster = classLoader.getResourceAsStream("tests/wrongExt.mp3");
        MultipartFile file = new MockMultipartFile("wrongExt.mp3", testPoster);

        Mockito.when(filmsRepository.existsByName(film.getName())).thenReturn(false);
        Mockito.when(filmsRepository.save(film)).thenReturn(film);
        Mockito.when(resourcesStorage.saveFile(Mockito.anyString(), Mockito.any(InputStream.class), Mockito.anyLong(), Mockito.nullable(String.class))).thenReturn(true);
        Mockito.when(filmImgResourcesRepository.save(Mockito.any(FilmImgResource.class))).thenThrow(RuntimeException.class);

        Assertions.assertThrows(FilmCreationException.class, () -> filmsService.saveFilm(film, file));
    }

    @Test
    public void saveFilmWithNullFilmDataTest() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream testPoster = classLoader.getResourceAsStream("tests/wrongExt.mp3");
        MultipartFile file = new MockMultipartFile("wrongExt.mp3", testPoster);

        Mockito.when(filmsRepository.existsById(film.getId())).thenReturn(true);
        Mockito.when(filmsRepository.existsByName(film.getName())).thenReturn(false);
        Mockito.when(filmsRepository.save(film)).thenReturn(film);
        Mockito.when(resourcesStorage.saveFile(Mockito.anyString(), Mockito.any(InputStream.class), Mockito.anyLong(), Mockito.nullable(String.class))).thenReturn(true);
        Mockito.when(filmImgResourcesRepository.save(Mockito.any(FilmImgResource.class))).thenReturn(filmImgResource);

        Assertions.assertThrows(NullPointerException.class, () -> filmsService.saveFilm(null, file));
    }

    @Test
    public void saveFilmWithNullFileTest() throws IOException {
        Mockito.when(filmsRepository.existsById(film.getId())).thenReturn(true);
        Mockito.when(filmsRepository.existsByName(film.getName())).thenReturn(false);
        Mockito.when(filmsRepository.save(film)).thenReturn(film);
        Mockito.when(resourcesStorage.saveFile(Mockito.anyString(), Mockito.any(InputStream.class), Mockito.anyLong(), Mockito.nullable(String.class))).thenReturn(true);
        Mockito.when(filmImgResourcesRepository.save(Mockito.any(FilmImgResource.class))).thenReturn(filmImgResource);

        Assertions.assertThrows(FilmCreationException.class, () -> filmsService.saveFilm(film, null));
    }

    @Test
    public void updateFilmTest() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream testPoster = classLoader.getResourceAsStream("tests/poster.jpg");
        MultipartFile file = new MockMultipartFile("poster.jpg", testPoster);

        Mockito.when(filmsRepository.existsById(film.getId())).thenReturn(true);
        Mockito.when(filmsRepository.existsByName(film.getName())).thenReturn(false);
        Mockito.when(filmsRepository.getReferenceById(1L)).thenReturn(film);
        Mockito.when(filmsRepository.save(film)).thenReturn(film);
        Mockito.when(resourcesStorage.saveFile(Mockito.anyString(), Mockito.any(InputStream.class), Mockito.anyLong(), Mockito.nullable(String.class))).thenReturn(true);
        Mockito.when(filmImgResourcesRepository.save(Mockito.any(FilmImgResource.class))).thenReturn(filmImgResource);

        FilmDto result = filmsService.updateFilm(film, file);
        Assertions.assertEquals(film.getId(), result.getId());
        Assertions.assertEquals(film.getName(), result.getName());
        Assertions.assertEquals(film.getYear(), result.getYear());
        Assertions.assertEquals(film.getDescription(), result.getDescription());
        Assertions.assertEquals(film.getGenre().getId(), result.getGenre().getId());
        Assertions.assertEquals(film.getGenre().getName(), result.getGenre().getName());
        Assertions.assertEquals(film.getDirector().getId(), result.getDirector().getId());
        Assertions.assertEquals(film.getDirector().getLastName(), result.getDirector().getLastName());
        Assertions.assertEquals(film.getDirector().getFirstName(), result.getDirector().getFirstName());
    }

    @Test
    public void updateNotExistedFilmTest() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream testPoster = classLoader.getResourceAsStream("tests/poster.jpg");
        MultipartFile file = new MockMultipartFile("poster.jpg", testPoster);

        Mockito.when(filmsRepository.existsById(film.getId())).thenReturn(false);

        Assertions.assertThrows(FilmNotFoundException.class, () -> filmsService.updateFilm(film, file));
    }

    @Test
    public void updateAlreadyExistedFilmTest() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream testPoster = classLoader.getResourceAsStream("tests/poster.jpg");
        MultipartFile file = new MockMultipartFile("poster.jpg", testPoster);

        Mockito.when(filmsRepository.existsById(film.getId())).thenReturn(true);
        Mockito.when(filmsRepository.existsByName(film.getName())).thenReturn(true);

        Assertions.assertThrows(FilmAlreadyExistException.class, () -> filmsService.updateFilm(film, file));
    }

    @Test
    public void updateFilmWithWrongFileExtensionTest() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream testPoster = classLoader.getResourceAsStream("tests/wrongExt.mp3");
        MultipartFile file = new MockMultipartFile("wrongExt.mp3", testPoster);

        Mockito.when(filmsRepository.existsById(film.getId())).thenReturn(true);
        Mockito.when(filmsRepository.existsByName(film.getName())).thenReturn(false);
        Mockito.when(filmsRepository.getReferenceById(1L)).thenReturn(film);
        Mockito.when(filmsRepository.save(film)).thenReturn(film);
        Mockito.when(resourcesStorage.saveFile(Mockito.anyString(), Mockito.any(InputStream.class), Mockito.anyLong(), Mockito.nullable(String.class))).thenReturn(true);
        Mockito.when(filmImgResourcesRepository.save(Mockito.any(FilmImgResource.class))).thenReturn(filmImgResource);

        Assertions.assertThrows(FilmUpdatingException.class, () -> filmsService.updateFilm(film, file));
    }

    @Test
    public void updateFilmWithRepositoryExceptionTest() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream testPoster = classLoader.getResourceAsStream("tests/wrongExt.mp3");
        MultipartFile file = new MockMultipartFile("wrongExt.mp3", testPoster);

        Mockito.when(filmsRepository.existsById(film.getId())).thenReturn(true);
        Mockito.when(filmsRepository.existsByName(film.getName())).thenReturn(false);
        Mockito.when(filmsRepository.getReferenceById(1L)).thenThrow(RuntimeException.class);

        Assertions.assertThrows(FilmUpdatingException.class, () -> filmsService.updateFilm(film, file));
    }

    @Test
    public void updateFilmWithImgResourceRepositoryExceptionTest() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream testPoster = classLoader.getResourceAsStream("tests/wrongExt.mp3");
        MultipartFile file = new MockMultipartFile("wrongExt.mp3", testPoster);

        Mockito.when(filmsRepository.existsById(film.getId())).thenReturn(true);
        Mockito.when(filmsRepository.existsByName(film.getName())).thenReturn(false);
        Mockito.when(filmsRepository.getReferenceById(1L)).thenReturn(film);
        Mockito.when(filmsRepository.save(film)).thenReturn(film);
        Mockito.when(resourcesStorage.saveFile(Mockito.anyString(), Mockito.any(InputStream.class), Mockito.anyLong(), Mockito.nullable(String.class))).thenReturn(true);
        Mockito.when(filmImgResourcesRepository.save(Mockito.any(FilmImgResource.class))).thenThrow(RuntimeException.class);

        Assertions.assertThrows(FilmUpdatingException.class, () -> filmsService.updateFilm(film, file));
    }

    @Test
    public void updateFilmWithNullFilmDataTest() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream testPoster = classLoader.getResourceAsStream("tests/wrongExt.mp3");
        MultipartFile file = new MockMultipartFile("wrongExt.mp3", testPoster);

        Mockito.when(filmsRepository.existsById(film.getId())).thenReturn(true);
        Mockito.when(filmsRepository.existsByName(film.getName())).thenReturn(false);
        Mockito.when(filmsRepository.getReferenceById(1L)).thenReturn(film);
        Mockito.when(filmsRepository.save(film)).thenReturn(film);
        Mockito.when(resourcesStorage.saveFile(Mockito.anyString(), Mockito.any(InputStream.class), Mockito.anyLong(), Mockito.nullable(String.class))).thenReturn(true);
        Mockito.when(filmImgResourcesRepository.save(Mockito.any(FilmImgResource.class))).thenReturn(filmImgResource);

        Assertions.assertThrows(NullPointerException.class, () -> filmsService.updateFilm(null, file));
    }

    @Test
    public void updateFilmWithNullFileTest() throws IOException {
        Mockito.when(filmsRepository.existsById(film.getId())).thenReturn(true);
        Mockito.when(filmsRepository.existsByName(film.getName())).thenReturn(false);
        Mockito.when(filmsRepository.getReferenceById(1L)).thenReturn(film);
        Mockito.when(filmsRepository.save(film)).thenReturn(film);
        Mockito.when(resourcesStorage.saveFile(Mockito.anyString(), Mockito.any(InputStream.class), Mockito.anyLong(), Mockito.nullable(String.class))).thenReturn(true);
        Mockito.when(filmImgResourcesRepository.save(Mockito.any(FilmImgResource.class))).thenReturn(filmImgResource);

        Assertions.assertThrows(FilmUpdatingException.class, () -> filmsService.updateFilm(film, null));
    }

    @Test
    public void getFilmPosterMetadataTest() {
        Mockito.when(filmsRepository.existsById(1L)).thenReturn(true);
        Mockito.when(filmsRepository.getReferenceById(1L)).thenReturn(film);

        FilmImgResourceDto result = filmsService.getFilmPosterMetadata(1L);
        Assertions.assertEquals(result.getId(), filmImgResource.getId());
        Assertions.assertEquals(result.getSize(), filmImgResource.getSize());
        Assertions.assertEquals(result.getFileName(), filmImgResource.getFileName());
        Assertions.assertEquals(result.getResolutionX(), filmImgResource.getResolutionX());
        Assertions.assertEquals(result.getResolutionY(), filmImgResource.getResolutionY());
        Assertions.assertEquals(result.getCreationTime(), filmImgResource.getCreationTime());
        Assertions.assertEquals(result.getUpdatingTime(), filmImgResource.getUpdatingTime());
        Assertions.assertEquals(result.getFileExtension(), filmImgResource.getFileExtension());
    }

    @Test
    public void getNotExistedFilmPosterMetadataTest() {
        Mockito.when(filmsRepository.existsById(Mockito.anyLong())).thenReturn(false);

        Assertions.assertThrows(FilmNotFoundException.class, () -> filmsService.getFilmPosterMetadata(1L));
    }

    @Test
    public void getFilmPosterMetadataWithRepositoryExceptionTest() {
        Mockito.when(filmsRepository.existsById(1L)).thenReturn(false);
        Mockito.when(filmsRepository.getReferenceById(1L)).thenThrow(RuntimeException.class);

        Assertions.assertThrows(RuntimeException.class, () -> filmsService.getFilmPosterMetadata(1L));
    }

    @Test
    public void getFilmPosterMetadataWithNullIdTest() {
        Mockito.when(filmsRepository.existsById(null)).thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class, () -> filmsService.getFilmPosterMetadata(null));
    }

    @Test
    public void getFilmPosterTest() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream testPoster = classLoader.getResourceAsStream("tests/poster.jpg");
        MultipartFile file = new MockMultipartFile("poster.jpg", testPoster);

        Mockito.when(filmsRepository.existsById(1L)).thenReturn(true);
        Mockito.when(filmsRepository.getReferenceById(1L)).thenReturn(film);
        Mockito.when(resourcesStorage.getFileByName(filmImgResource.getFileName())).thenReturn(file.getInputStream());

        InputStream result = filmsService.getFilmPoster(1L);
        Assertions.assertNotNull(result);
    }


    @Test
    public void getNotExistedFilmPosterTest() {
        Mockito.when(filmsRepository.existsById(Mockito.anyLong())).thenReturn(false);

        Assertions.assertThrows(FilmNotFoundException.class, () -> filmsService.getFilmPoster(1L));
    }

    @Test
    public void getFilmPosterWithRepositoryExceptionTest() {
        Mockito.when(filmsRepository.existsById(1L)).thenReturn(false);
        Mockito.when(filmsRepository.getReferenceById(1L)).thenThrow(RuntimeException.class);

        Assertions.assertThrows(RuntimeException.class, () -> filmsService.getFilmPoster(1L));
    }

    @Test
    public void getFilmPosterWithNullIdTest() {
        Mockito.when(filmsRepository.existsById(null)).thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class, () -> filmsService.getFilmPoster(null));
    }

    @Test
    public void deleteFilmTest() {
        Mockito.when(filmsRepository.existsById(1L)).thenReturn(true);
        Mockito.when(filmsRepository.getReferenceById(1L)).thenReturn(film);
        Mockito.doNothing().when(resourcesStorage).deleteFileByName(Mockito.anyString());
        Mockito.doNothing().when(filmImgResourcesRepository).delete(Mockito.any(FilmImgResource.class));
        Mockito.doNothing().when(filmsRepository).delete(Mockito.any(Film.class));

        Assertions.assertEquals(1L, filmsService.deleteFilm(1L));
    }

    @Test
    public void deleteNotExistedFilmTest() {
        Mockito.when(filmsRepository.existsById(1L)).thenReturn(false);

        Assertions.assertThrows(FilmNotFoundException.class, () -> filmsService.deleteFilm(1L));
    }

    @Test
    public void deleteFilmWithRepositoryExceptionTest() {
        Mockito.when(filmsRepository.existsById(1L)).thenReturn(true);
        Mockito.when(filmsRepository.getReferenceById(1L)).thenThrow(RuntimeException.class);

        Assertions.assertThrows(FilmDeletingException.class, () -> filmsService.deleteFilm(1L));
    }

    @Test
    public void deleteFilmWithImgRepositoryExceptionTest() {
        Mockito.when(filmsRepository.existsById(1L)).thenReturn(true);
        Mockito.when(filmsRepository.getReferenceById(1L)).thenReturn(film);
        Mockito.doNothing().when(resourcesStorage).deleteFileByName(Mockito.anyString());
        Mockito.doThrow(RuntimeException.class).when(filmImgResourcesRepository).delete(Mockito.any(FilmImgResource.class));

        Assertions.assertThrows(FilmDeletingException.class, () -> filmsService.deleteFilm(1L));
    }

    @Test
    public void deleteFilmWithResourceStorageExceptionTest() {
        Mockito.when(filmsRepository.existsById(1L)).thenReturn(true);
        Mockito.when(filmsRepository.getReferenceById(1L)).thenReturn(film);
        Mockito.doThrow(RuntimeException.class).when(resourcesStorage).deleteFileByName(Mockito.anyString());

        Assertions.assertThrows(FilmDeletingException.class, () -> filmsService.deleteFilm(1L));
    }

    @Test
    public void deleteNotExistedFilmWithNullIdTest() {
        Mockito.when(filmsRepository.existsById(null)).thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class, () -> filmsService.deleteFilm(null));
    }

}
