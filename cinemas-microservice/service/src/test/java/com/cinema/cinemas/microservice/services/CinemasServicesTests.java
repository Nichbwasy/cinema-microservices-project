package com.cinema.cinemas.microservice.services;

import com.cinema.cinemas.microservice.domains.CinemaDto;
import com.cinema.cinemas.microservice.domains.HallDto;
import com.cinema.cinemas.microservice.exceptions.services.cinemas.*;
import com.cinema.cinemas.microservice.exceptions.services.halls.HallCreationException;
import com.cinema.cinemas.microservice.models.Cinema;
import com.cinema.cinemas.microservice.models.Hall;
import com.cinema.cinemas.microservice.repositories.CinemasRepository;
import com.cinema.cinemas.microservice.repositories.HallsRepository;
import com.cinema.cinemas.microservice.services.configs.CinemasServiceTestsConfiguration;
import com.cinema.cinemas.microservice.services.configs.HallsServiceTestsConfiguration;
import com.cinema.cinemas.microservice.services.mappers.HallMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(classes = CinemasService.class)
@Import(CinemasServiceTestsConfiguration.class)
@ExtendWith(SpringExtension.class)
public class CinemasServicesTests {

    private Cinema cinema = new Cinema(1L, "cinema", "description", new ArrayList<>());
    private Hall hall = new Hall(1L, "hall", "description", 1, 1, 1, new ArrayList<>());

    @Autowired
    private CinemasService cinemasService;

    @MockBean
    private CinemasRepository cinemasRepository;

    @MockBean
    private HallsService hallsService;

    @MockBean
    private HallsRepository hallsRepository;

    @Test
    public void getAllCinemasTest() {
        Mockito.when(cinemasRepository.findAll()).thenReturn(Arrays.asList(cinema));

        List<CinemaDto> cinemasDto = cinemasService.getAllCinemas();
        Assertions.assertEquals(1L, cinemasDto.size());
        Assertions.assertEquals(cinemasDto.get(0).getId(), cinema.getId());
        Assertions.assertEquals(cinemasDto.get(0).getName(), cinema.getName());
        Assertions.assertEquals(cinemasDto.get(0).getDescription(), cinema.getDescription());
    }

    @Test
    public void getCinemaTest() {
        Mockito.when(cinemasRepository.existsById(1L)).thenReturn(true);
        Mockito.when(cinemasRepository.getReferenceById(1L)).thenReturn(cinema);

        CinemaDto result = cinemasService.getCinema(1L);
        Assertions.assertEquals(result.getId(), cinema.getId());
        Assertions.assertEquals(result.getName(), cinema.getName());
        Assertions.assertEquals(result.getDescription(), cinema.getDescription());
    }

    @Test
    public void getCinemaNotExistTest() {
        Mockito.when(cinemasRepository.existsById(Mockito.anyLong())).thenReturn(false);

        Assertions.assertThrows(CinemaNotFoundException.class, () -> cinemasService.getCinema(1L));
    }

    @Test
    public void getCinemaNullTest() {
        Mockito.when(cinemasRepository.existsById(Mockito.anyLong())).thenReturn(false);

        Assertions.assertThrows(CinemaNotFoundException.class, () -> cinemasService.getCinema(null));
    }

    @Test
    public void createCinemaTest() {
        Mockito.when(cinemasRepository.existsById(Mockito.anyLong())).thenReturn(false);
        Mockito.when(cinemasRepository.save(Mockito.any(Cinema.class))).thenReturn(cinema);

        CinemaDto result = cinemasService.createCinema(cinema);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), cinema.getId());
        Assertions.assertEquals(result.getName(), cinema.getName());
        Assertions.assertEquals(result.getDescription(), cinema.getDescription());
    }

    @Test
    public void createAlreadyExistedCinemaTest() {
        Mockito.when(cinemasRepository.existsByName("cinema")).thenReturn(true);

        Assertions.assertThrows(CinemaAlreadyExistException.class, () -> cinemasService.createCinema(cinema));
    }

    @Test
    public void createCinemaSavingExceptionTest() {
        Mockito.when(cinemasRepository.existsById(1L)).thenReturn(false);
        Mockito.when(cinemasRepository.save(Mockito.any(Cinema.class))).thenThrow(RuntimeException.class);

        Assertions.assertThrows(CinemaCreationException.class, () -> cinemasService.createCinema(cinema));
    }

    @Test
    public void createCinemaSavingNullTest() {
        Assertions.assertThrows(NullPointerException.class, () -> cinemasService.createCinema(null));
    }

    @Test
    public void updateCinemaTest() {
        Mockito.when(cinemasRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.when(cinemasRepository.save(Mockito.any(Cinema.class))).thenReturn(cinema);

        CinemaDto result = cinemasService.updateCinema(cinema);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), cinema.getId());
        Assertions.assertEquals(result.getName(), cinema.getName());
        Assertions.assertEquals(result.getDescription(), cinema.getDescription());
    }

    @Test
    public void updateCinemaSavingExceptionTest() {
        Mockito.when(cinemasRepository.existsById(1L)).thenReturn(true);
        Mockito.when(cinemasRepository.save(Mockito.any(Cinema.class))).thenThrow(RuntimeException.class);

        Assertions.assertThrows(CinemaUpdatingException.class, () -> cinemasService.updateCinema(cinema));
    }

    @Test
    public void updateCinemaSavingNullTest() {
        Assertions.assertThrows(NullPointerException.class, () -> cinemasService.updateCinema(null));
    }

    @Test
    public void deleteCinemaTest() {
        Mockito.when(cinemasRepository.existsById(1L)).thenReturn(true);
        Mockito.when(cinemasRepository.existsById(5L)).thenReturn(true);
        Mockito.doNothing().when(cinemasRepository).deleteById(Mockito.anyLong());

        List<Long> result = cinemasService.deleteCinemasByIds(Arrays.asList(1L, 3L, 5L));
        Assertions.assertEquals(result.size(), 2);
        Assertions.assertEquals(result.get(0), 1L);
        Assertions.assertEquals(result.get(1), 5L);
    }

    @Test
    public void deleteNullCinema() {
        Assertions.assertThrows(CinemaDeletingException.class, () -> cinemasService.deleteCinemasByIds(null));
    }

    @Test
    public void addHallTest() {
        Mockito.when(cinemasRepository.existsById(1L)).thenReturn(true);
        Mockito.when(cinemasRepository.getReferenceById(1L)).thenReturn(cinema);
        Mockito.when(hallsRepository.getReferenceById(1L)).thenReturn(hall);
        Mockito.when(hallsService.createHall(1L, hall)).thenReturn(HallMapper.INSTANCE.mapToDto(hall));

        HallDto hallDto = cinemasService.addHall(1L, 1L, hall);
        Assertions.assertNotNull(hallDto);
        Assertions.assertEquals(hallDto.getId(), hall.getId());
        Assertions.assertEquals(hallDto.getDescription(), hall.getDescription());
        Assertions.assertEquals(hallDto.getName(), hall.getName());
        Assertions.assertEquals(hallDto.getRows(), hall.getRows());
        Assertions.assertEquals(hallDto.getPlacesInRow(), hall.getPlacesInRow());
        Assertions.assertEquals(hallDto.getSize(), hall.getSize());
    }

    @Test
    public void addHallToNotExistedCinemaTest() {
        Mockito.when(cinemasRepository.existsById(1L)).thenReturn(false);

        Assertions.assertThrows(CinemaNotFoundException.class, () -> cinemasService.addHall(1L, 1L, hall));
    }

    @Test
    public void addHallWithExceptionTest() {
        Mockito.when(cinemasRepository.existsById(1L)).thenReturn(true);
        Mockito.when(cinemasRepository.getReferenceById(Mockito.anyLong())).thenThrow(RuntimeException.class);

        Assertions.assertThrows(HallCreationException.class, () -> cinemasService.addHall(1L, 1L, hall));
    }

    @Test
    public void addHallNullCinemaTest() {
        Mockito.when(cinemasRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.when(cinemasRepository.getReferenceById(Mockito.anyLong())).thenThrow(RuntimeException.class);

        Assertions.assertThrows(CinemaNotFoundException.class, () -> cinemasService.addHall(null, 1L, hall));
    }

    @Test
    public void addHallNullSitTypeTest() {
        Mockito.when(cinemasRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.when(cinemasRepository.getReferenceById(Mockito.anyLong())).thenThrow(HallCreationException.class);

        Assertions.assertThrows(HallCreationException.class, () -> cinemasService.addHall(1L, null, hall));
    }

    @Test
    public void addHallNullHallDataTest() {
        Mockito.when(cinemasRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.when(cinemasRepository.getReferenceById(Mockito.anyLong())).thenThrow(HallCreationException.class);

        Assertions.assertThrows(HallCreationException.class, () -> cinemasService.addHall(1L, 1L, null));
    }


}
