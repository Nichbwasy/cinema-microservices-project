package com.cinema.films.microservice.services;

import com.cinema.films.microservice.domains.GenreDto;
import com.cinema.films.microservice.exceptions.services.genres.*;
import com.cinema.films.microservice.models.Genre;
import com.cinema.films.microservice.repositories.GenresRepository;
import com.cinema.films.microservice.services.configs.GenresServiceTestsConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@SpringBootTest(classes = GenresService.class)
@Import(GenresServiceTestsConfiguration.class)
@ExtendWith(SpringExtension.class)
public class GenresServiceTests {

    private static final Genre genre = new Genre(1L, "genre_name");

    @Autowired
    private GenresService genresService;

    @MockBean
    private GenresRepository genresRepository;

    @Test
    public void getAllGenresTest() {
        Mockito.when(genresRepository.findAll()).thenReturn(Collections.singletonList(genre));

        List<GenreDto> result = genresService.getAllGenres();
        Assertions.assertEquals(1L, result.size());
        Assertions.assertEquals(genre.getId(), result.get(0).getId());
        Assertions.assertEquals(genre.getName(), result.get(0).getName());

    }

    @Test
    public void getAllGenresWithServiceExceptionTest() {
        Mockito.when(genresRepository.findAll()).thenThrow(RuntimeException.class);

        Assertions.assertThrows(RuntimeException.class, () -> genresService.getAllGenres());
    }


    @Test
    public void getGenreTest() {
        Mockito.when(genresRepository.existsById(1L)).thenReturn(true);
        Mockito.when(genresRepository.getReferenceById(1L)).thenReturn(genre);

        GenreDto result = genresService.getGenre(1L);
        Assertions.assertEquals(result.getId(), genre.getId());
        Assertions.assertEquals(result.getName(), genre.getName());
    }

    @Test
    public void getNotExistedGenreTest() {
        Mockito.when(genresRepository.existsById(1L)).thenReturn(false);

        Assertions.assertThrows(GenreNotFoundException.class, () -> genresService.getGenre(1L));
    }

    @Test
    public void getGenreWithRepositoryExceptionTest() {
        Mockito.when(genresRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.when(genresRepository.getReferenceById(1L)).thenThrow(RuntimeException.class);

        Assertions.assertThrows(RuntimeException.class, () -> genresService.getGenre(1L));
    }

    @Test
    public void createGenreTest() {
        Mockito.when(genresRepository.existsByName(genre.getName())).thenReturn(false);
        Mockito.when(genresRepository.save(genre)).thenReturn(genre);

        GenreDto result = genresService.createGenre(genre);
        Assertions.assertEquals(result.getId(), genre.getId());
        Assertions.assertEquals(result.getName(), genre.getName());
    }

    @Test
    public void createAlreadyExistedGenreTest() {
        Mockito.when(genresRepository.existsByName(genre.getName())).thenReturn(true);

        Assertions.assertThrows(GenreAlreadyExistException.class, () -> genresService.createGenre(genre));
    }

    @Test
    public void createGenreWithRepositoryExceptionTest() {
        Mockito.when(genresRepository.existsByName(genre.getName())).thenReturn(false);
        Mockito.when(genresRepository.save(genre)).thenThrow(RuntimeException.class);

        Assertions.assertThrows(GenreCreationException.class, () -> genresService.createGenre(genre));
    }

    @Test
    public void updateGenreTest() {
        Mockito.when(genresRepository.existsById(genre.getId())).thenReturn(true);
        Mockito.when(genresRepository.save(genre)).thenReturn(genre);

        GenreDto result = genresService.updateGenre(genre);
        Assertions.assertEquals(result.getId(), genre.getId());
        Assertions.assertEquals(result.getName(), genre.getName());
    }

    @Test
    public void updateNotExistedGenreTest() {
        Mockito.when(genresRepository.existsById(genre.getId())).thenReturn(false);

        Assertions.assertThrows(GenreNotFoundException.class, () -> genresService.updateGenre(genre));
    }

    @Test
    public void updateGenreWithRepositoryExceptionTest() {
        Mockito.when(genresRepository.existsById(genre.getId())).thenReturn(true);
        Mockito.when(genresRepository.save(genre)).thenThrow(RuntimeException.class);

        Assertions.assertThrows(GenreUpdatingException.class, () -> genresService.updateGenre(genre));
    }

    @Test
    public void deleteGenreTest() {
        Mockito.when(genresRepository.existsById(genre.getId())).thenReturn(true);
        Mockito.doNothing().when(genresRepository).deleteById(1L);

        Assertions.assertEquals(1L, genresService.deleteGenre(1L));
    }

    @Test
    public void deleteNotExistedGenreTest() {
        Mockito.when(genresRepository.existsById(genre.getId())).thenReturn(false);

        Assertions.assertThrows(GenreNotFoundException.class, () -> genresService.deleteGenre(1L));
    }

    @Test
    public void deleteGenreWithRepositoryExceptionTest() {
        Mockito.when(genresRepository.existsById(genre.getId())).thenReturn(true);
        Mockito.doThrow(RuntimeException.class).when(genresRepository).deleteById(1L);

        Assertions.assertThrows(GenreDeletingException.class, () -> genresService.deleteGenre(1L));
    }
}
