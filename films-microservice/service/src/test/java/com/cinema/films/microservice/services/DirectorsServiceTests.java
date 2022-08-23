package com.cinema.films.microservice.services;

import com.cinema.films.microservice.domains.DirectorDto;
import com.cinema.films.microservice.exceptions.services.directors.*;
import com.cinema.films.microservice.models.Director;
import com.cinema.films.microservice.repositories.DirectorsRepository;
import com.cinema.films.microservice.services.configs.DirectorsServiceTestsConfiguration;
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

@SpringBootTest(classes = DirectorsService.class)
@Import(DirectorsServiceTestsConfiguration.class)
@ExtendWith(SpringExtension.class)
public class DirectorsServiceTests {

    private final static Director director = new Director(1L, "first_name", "last_name");

    @Autowired
    private DirectorsService directorsService;

    @MockBean
    private DirectorsRepository directorsRepository;

    @Test
    public void getAllDirectorsTest() {
        Mockito.when(directorsRepository.findAll()).thenReturn(Collections.singletonList(director));

        List<DirectorDto> result = directorsService.getAllDirectors();
        Assertions.assertEquals(1L, result.size());
        Assertions.assertEquals(director.getId(), result.get(0).getId());
        Assertions.assertEquals(director.getFirstName(), result.get(0).getFirstName());
        Assertions.assertEquals(director.getLastName(), result.get(0).getLastName());
    }

    @Test
    public void getAllDirectorsWithServiceExceptionTest() {
        Mockito.when(directorsRepository.findAll()).thenThrow(RuntimeException.class);

        Assertions.assertThrows(RuntimeException.class, () -> directorsService.getAllDirectors());
    }

    @Test
    public void getDirectorTest() {
        Mockito.when(directorsRepository.existsById(1L)).thenReturn(true);
        Mockito.when(directorsRepository.getReferenceById(1L)).thenReturn(director);

        DirectorDto result = directorsService.getDirector(1L);
        Assertions.assertEquals(director.getId(), result.getId());
        Assertions.assertEquals(director.getFirstName(), result.getFirstName());
        Assertions.assertEquals(director.getLastName(), result.getLastName());
    }

    @Test
    public void getNotExistedDirectorTest() {
        Mockito.when(directorsRepository.existsById(1L)).thenReturn(false);

        Assertions.assertThrows(DirectorNotFoundException.class, () -> directorsService.getDirector(1L));
    }

    @Test
    public void getDirectorWithRepositoryExceptionTest() {
        Mockito.when(directorsRepository.existsById(1L)).thenReturn(true);
        Mockito.when(directorsRepository.getReferenceById(1L)).thenThrow(RuntimeException.class);

        Assertions.assertThrows(RuntimeException.class, () -> directorsService.getDirector(1L));
    }

    @Test
    public void createDirectorTest() {
        Mockito.when(directorsRepository.existsByFirstNameAndLastName(director.getFirstName(), director.getLastName())).thenReturn(false);
        Mockito.when(directorsRepository.save(director)).thenReturn(director);

        DirectorDto result = directorsService.createDirector(director);
        Assertions.assertEquals(director.getId(), result.getId());
        Assertions.assertEquals(director.getFirstName(), result.getFirstName());
        Assertions.assertEquals(director.getLastName(), result.getLastName());
    }

    @Test
    public void createAlreadyExistedDirectorTest() {
        Mockito.when(directorsRepository.existsByFirstNameAndLastName(director.getFirstName(), director.getLastName())).thenReturn(true);

        Assertions.assertThrows(DirectorAlreadyExistException.class, () -> directorsService.createDirector(director));
    }

    @Test
    public void createDirectorWithRepositoryExceptionTest() {
        Mockito.when(directorsRepository.existsByFirstNameAndLastName(director.getFirstName(), director.getLastName())).thenReturn(false);
        Mockito.when(directorsRepository.save(director)).thenThrow(RuntimeException.class);

        Assertions.assertThrows(DirectorCreationException.class, () -> directorsService.createDirector(director));
    }

    @Test
    public void updateDirectorTest() {
        Mockito.when(directorsRepository.existsById(1L)).thenReturn(true);
        Mockito.when(directorsRepository.save(director)).thenReturn(director);

        DirectorDto result = directorsService.updateDirector(director);
        Assertions.assertEquals(director.getId(), result.getId());
        Assertions.assertEquals(director.getFirstName(), result.getFirstName());
        Assertions.assertEquals(director.getLastName(), result.getLastName());
    }

    @Test
    public void updateNotExistedDirectorTest() {
        Mockito.when(directorsRepository.existsById(1L)).thenReturn(false);

        Assertions.assertThrows(DirectorNotFoundException.class, () -> directorsService.updateDirector(director));
    }

    @Test
    public void updateDirectorWithRepositoryExceptionTest() {
        Mockito.when(directorsRepository.existsById(1L)).thenReturn(true);
        Mockito.when(directorsRepository.save(director)).thenThrow(RuntimeException.class);

        Assertions.assertThrows(DirectorUpdatingException.class, () -> directorsService.updateDirector(director));
    }

    @Test
    public void deleteDirectorTest() {
        Mockito.when(directorsRepository.existsById(1L)).thenReturn(true);
        Mockito.doNothing().when(directorsRepository).deleteById(1L);

        Assertions.assertEquals(1L, directorsService.deleteDirector(1L));
    }

    @Test
    public void deleteNotExistedDirectorTest() {
        Mockito.when(directorsRepository.existsById(1L)).thenReturn(false);

        Assertions.assertThrows(DirectorNotFoundException.class, () -> directorsService.deleteDirector(1L));
    }

    @Test
    public void deleteDirectorWithRepositoryExceptionTest() {
        Mockito.when(directorsRepository.existsById(1L)).thenReturn(true);
        Mockito.doThrow(RuntimeException.class).when(directorsRepository).deleteById(1L);

        Assertions.assertThrows(DirectorDeletingException.class, () -> directorsService.deleteDirector(1L));
    }

}
