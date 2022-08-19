package com.cinema.cinemas.microservice.services;

import com.cinema.cinemas.microservice.domains.HallDto;
import com.cinema.cinemas.microservice.domains.SitDto;
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
import com.cinema.cinemas.microservice.repositories.CinemasRepository;
import com.cinema.cinemas.microservice.repositories.HallsRepository;
import com.cinema.cinemas.microservice.repositories.SitsRepository;
import com.cinema.cinemas.microservice.repositories.SitsTypesRepository;
import com.cinema.cinemas.microservice.services.configs.HallsServiceTestsConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

@SpringBootTest(classes = HallsService.class)
@Import(HallsServiceTestsConfiguration.class)
@ExtendWith(SpringExtension.class)
public class HallsServiceTests {

    private Cinema cinema = new Cinema(1L, "cinema", "description", new ArrayList<>());
    private Hall hall = new Hall(1L, "hall", "description", 1, 1, 1, new ArrayList<>());
    private Sit sit = new Sit(1L, 1, 1, null);
    private SitType sitType = new SitType(1L, "name", "description", 1);

    @Autowired
    private HallsService hallsService;

    @MockBean
    private HallsRepository hallsRepository;

    @MockBean
    private CinemasRepository cinemasRepository;

    @MockBean
    private SitsRepository sitsRepository;

    @MockBean
    private SitsTypesRepository sitsTypesRepository;

    @BeforeEach
    public void init() {
        sit.setType(sitType);
        hall.setSits(Arrays.asList(sit));
        cinema.setHalls(Arrays.asList(hall));
    }

    @Test
    public void getAllHallsInCinemaTest() {
        Mockito.when(cinemasRepository.existsById(1L)).thenReturn(true);
        Mockito.when(cinemasRepository.getReferenceById(1L)).thenReturn(cinema);

        List<HallDto> result = hallsService.getAllHallsInCinema(1L);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(hall.getId(), result.get(0).getId());
        Assertions.assertEquals(hall.getName(), result.get(0).getName());
        Assertions.assertEquals(hall.getRows(), result.get(0).getRows());
        Assertions.assertEquals(hall.getSize(), result.get(0).getSize());
        Assertions.assertEquals(hall.getPlacesInRow(), result.get(0).getPlacesInRow());
        Assertions.assertEquals(hall.getDescription(), result.get(0).getDescription());
    }

    @Test
    public void getAllHalsInNotExistedCinemaTest() {
        Mockito.when(cinemasRepository.existsById(Mockito.anyLong())).thenReturn(false);

        Assertions.assertThrows(CinemaNotFoundException.class, () -> hallsService.getAllHallsInCinema(1L));
    }

    @Test
    public void getAllHalsInNullableCinemaTest() {
        Mockito.when(cinemasRepository.existsById(null)).thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class, () -> hallsService.getAllHallsInCinema(null));
    }

    @Test
    public void getCinemaHallTest() {
        Mockito.when(cinemasRepository.existsById(1L)).thenReturn(true);
        Mockito.when(cinemasRepository.getReferenceById(1L)).thenReturn(cinema);
        Mockito.when(hallsRepository.getReferenceById(1L)).thenReturn(hall);

        HallDto result = hallsService.getCinemaHall(1L, 1L);
        Assertions.assertEquals(hall.getId(), result.getId());
        Assertions.assertEquals(hall.getName(), result.getName());
        Assertions.assertEquals(hall.getRows(), result.getRows());
        Assertions.assertEquals(hall.getSize(), result.getSize());
        Assertions.assertEquals(hall.getPlacesInRow(), result.getPlacesInRow());
        Assertions.assertEquals(hall.getDescription(), result.getDescription());
    }

    @Test
    public void getCinemaHallWhereCinemaNotFoundTest() {
        Mockito.when(cinemasRepository.existsById(1L)).thenReturn(false);

        Assertions.assertThrows(CinemaNotFoundException.class, () -> hallsService.getCinemaHall(1L, 1L));
    }

    @Test
    public void getCinemaHallWhereCinemaNullTest() {
        Mockito.when(cinemasRepository.existsById(null)).thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class, () -> hallsService.getCinemaHall(null, 1L));
    }

    @Test
    public void getCinemaHallWhereHallNotBelongCinemaTest() {
        Mockito.when(cinemasRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.when(cinemasRepository.getReferenceById(Mockito.anyLong())).thenReturn(
                new Cinema(1L, "cinema", "description", new ArrayList<>()
                ));
        Assertions.assertThrows(HallNotFoundException.class, () -> hallsService.getCinemaHall(1L, 1L));
    }

    @Test
    public void getCinemaHallWhereHallNullTest() {
        Mockito.when(cinemasRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.when(cinemasRepository.getReferenceById(Mockito.anyLong())).thenReturn(cinema);

        Assertions.assertThrows(HallNotFoundException.class, () -> hallsService.getCinemaHall(1L, null));
    }

    @Test
    public void createHallTest() {
        Mockito.when(hallsRepository.save(Mockito.any(Hall.class))).thenReturn(hall);
        Mockito.when(sitsTypesRepository.existsById(1L)).thenReturn(true);
        Mockito.when(sitsTypesRepository.getReferenceById(1L)).thenReturn(sitType);
        Mockito.when(sitsRepository.save(Mockito.any(Sit.class))).thenReturn(sit);

        HallDto result = hallsService.createHall(1L, hall);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(hall.getId(), result.getId());
        Assertions.assertEquals(hall.getName(), result.getName());
        Assertions.assertEquals(hall.getSize(), result.getSize());
        Assertions.assertEquals(hall.getRows(), result.getRows());
        Assertions.assertEquals(hall.getPlacesInRow(), result.getPlacesInRow());
        Assertions.assertEquals(hall.getDescription(), result.getDescription());
        Assertions.assertEquals(hall.getSits().get(0).getId(), result.getSits().get(0).getId());
        Assertions.assertEquals(hall.getSits().get(0).getRow(), result.getSits().get(0).getRow());
        Assertions.assertEquals(hall.getSits().get(0).getPlace(), result.getSits().get(0).getPlace());
        Assertions.assertEquals(hall.getSits().get(0).getType().getId(), result.getSits().get(0).getType().getId());
        Assertions.assertEquals(hall.getSits().get(0).getType().getName(), result.getSits().get(0).getType().getName());
        Assertions.assertEquals(hall.getSits().get(0).getType().getCapacity(), result.getSits().get(0).getType().getCapacity());
        Assertions.assertEquals(hall.getSits().get(0).getType().getDescription(), result.getSits().get(0).getType().getDescription());
    }

    @Test
    public void createHallWithRepositoryExceptionTest() {
        Mockito.when(hallsRepository.save(Mockito.any(Hall.class))).thenReturn(hall);
        Mockito.when(sitsTypesRepository.existsById(1L)).thenReturn(true);
        Mockito.when(sitsTypesRepository.getReferenceById(1L)).thenThrow(RuntimeException.class);

        Assertions.assertThrows(HallCreationException.class, () -> hallsService.createHall(1L, hall));
    }

    @Test
    public void createHallWhenSitTypeNotExistTest() {
        Mockito.when(hallsRepository.save(Mockito.any(Hall.class))).thenReturn(hall);
        Mockito.when(sitsTypesRepository.existsById(1L)).thenReturn(false);

        Assertions.assertThrows(SitTypeNotFoundException.class, () -> hallsService.createHall(1L, hall));
    }

    @Test
    public void createHallWhenSitTypeNullTest() {
        Mockito.when(hallsRepository.save(Mockito.any(Hall.class))).thenReturn(hall);
        Mockito.when(sitsTypesRepository.existsById(null)).thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class, () -> hallsService.createHall(null, hall));
    }

    @Test
    public void createHallWhenHallDataNullTest() {
        Mockito.when(sitsTypesRepository.existsById(1L)).thenReturn(true);
        Mockito.when(sitsTypesRepository.getReferenceById(1L)).thenReturn(sitType);

        Assertions.assertThrows(HallCreationException.class, () -> hallsService.createHall(1L, null));
    }

    @Test
    public void updateHallTest() {
        Mockito.when(hallsRepository.existsById(1L)).thenReturn(true);
        Mockito.when(hallsRepository.save(Mockito.any(Hall.class))).thenReturn(hall);

        HallDto result = hallsService.updateHall(hall);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(hall.getId(), result.getId());
        Assertions.assertEquals(hall.getName(), result.getName());
        Assertions.assertEquals(hall.getSize(), result.getSize());
        Assertions.assertEquals(hall.getRows(), result.getRows());
        Assertions.assertEquals(hall.getPlacesInRow(), result.getPlacesInRow());
        Assertions.assertEquals(hall.getDescription(), result.getDescription());
        Assertions.assertEquals(hall.getSits().get(0).getId(), result.getSits().get(0).getId());
        Assertions.assertEquals(hall.getSits().get(0).getRow(), result.getSits().get(0).getRow());
        Assertions.assertEquals(hall.getSits().get(0).getPlace(), result.getSits().get(0).getPlace());
        Assertions.assertEquals(hall.getSits().get(0).getType().getId(), result.getSits().get(0).getType().getId());
        Assertions.assertEquals(hall.getSits().get(0).getType().getName(), result.getSits().get(0).getType().getName());
        Assertions.assertEquals(hall.getSits().get(0).getType().getCapacity(), result.getSits().get(0).getType().getCapacity());
        Assertions.assertEquals(hall.getSits().get(0).getType().getDescription(), result.getSits().get(0).getType().getDescription());
    }

    @Test
    public void updateHallWhichNotExistTest() {
        Mockito.when(hallsRepository.existsById(1L)).thenReturn(false);

        Assertions.assertThrows(HallNotFoundException.class, () -> hallsService.updateHall(hall));
    }

    @Test
    public void updateHallWhichRepositoryExceptionTest() {
        Mockito.when(hallsRepository.existsById(1L)).thenReturn(true);
        Mockito.when(hallsRepository.getReferenceById(Mockito.anyLong())).thenThrow(RuntimeException.class);

        Assertions.assertThrows(HallUpdatingException.class, () -> hallsService.updateHall(hall));
    }

    @Test
    public void updateHallWhichNullHallTest() {
        Mockito.when(hallsRepository.existsById(1L)).thenReturn(true);
        Mockito.when(hallsRepository.getReferenceById(null)).thenThrow(NullPointerException.class);

        Assertions.assertThrows(HallUpdatingException.class, () -> hallsService.updateHall(hall));
    }

    @Test
    public void deleteHallFromCinemaTest() {
        Mockito.when(cinemasRepository.existsById(1L)).thenReturn(true);
        Mockito.when(cinemasRepository.getReferenceById(1L)).thenReturn(cinema);
        Mockito.doNothing().when(hallsRepository).deleteById(1L);

        Long result = hallsService.deleteHallFromCinema(1L, 1L);
        Assertions.assertEquals(1L, result);
    }

    @Test
    public void deleteHallFromNotExistedCinemaTest() {
        Mockito.when(cinemasRepository.existsById(1L)).thenReturn(false);

        Assertions.assertThrows(CinemaNotFoundException.class, () -> hallsService.deleteHallFromCinema(1L, 1L));
    }

    @Test
    public void deleteHallFromCinemaWithRepositoryExceptionTest() {
        Mockito.when(cinemasRepository.existsById(1L)).thenReturn(true);
        Mockito.when(cinemasRepository.getReferenceById(1L)).thenReturn(cinema);
        Mockito.doThrow(RuntimeException.class).when(hallsRepository).deleteById(Mockito.anyLong());

        Assertions.assertThrows(HallDeletingException.class, () -> hallsService.deleteHallFromCinema(1L, 1L));
    }

    @Test
    public void deleteHallFromCinemaWithNullCinemaIdTest() {
        Mockito.when(cinemasRepository.existsById(null)).thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class, () -> hallsService.deleteHallFromCinema(null, 1L));
    }

    @Test
    public void deleteHallFromCinemaWithNullHallIdTest() {
        Mockito.when(cinemasRepository.existsById(1L)).thenReturn(true);
        Mockito.when(cinemasRepository.getReferenceById(1L)).thenReturn(cinema);
        Mockito.doThrow(NullPointerException.class).when(hallsRepository).deleteById(null);

        Assertions.assertThrows(HallNotFoundException.class, () -> hallsService.deleteHallFromCinema(1L, null));
    }

    @Test
    public void deleteAllHallsFromCinemaTest() {
        Mockito.when(cinemasRepository.existsById(1L)).thenReturn(true);
        Mockito.when(cinemasRepository.getReferenceById(1L)).thenReturn(cinema);
        Mockito.doNothing().when(hallsRepository).deleteAllById(Mockito.anyCollection());

        List<Long> result = hallsService.deleteAllHallsFromCinema(1L);
        Assertions.assertEquals(1L, result.size());
        Assertions.assertEquals(1L, result.get(0));
    }

    @Test
    public void deleteAllHallsFromNotExistedCinemaTest() {
        Mockito.when(cinemasRepository.existsById(Mockito.anyLong())).thenReturn(false);

        Assertions.assertThrows(CinemaNotFoundException.class, () -> hallsService.deleteAllHallsFromCinema(1L));
    }

    @Test
    public void deleteAllHallsFromCinemaWithRepositoryExceptionTest() {
        Mockito.when(cinemasRepository.existsById(1L)).thenReturn(true);
        Mockito.when(cinemasRepository.getReferenceById(1L)).thenReturn(cinema);
        Mockito.doThrow(RuntimeException.class).when(hallsRepository).deleteAllById(Mockito.anyCollection());

        Assertions.assertThrows(HallDeletingException.class, () -> hallsService.deleteAllHallsFromCinema(1L));
    }

    @Test
    public void deleteAllHallsFromCinemaWithNullCinemaIdTest() {
        Mockito.when(cinemasRepository.existsById(null)).thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class, () -> hallsService.deleteAllHallsFromCinema(null));
    }

    @Test
    public void getAllSitsInHallTest() {
        Mockito.when(hallsRepository.existsById(1L)).thenReturn(true);
        Mockito.when(hallsRepository.getReferenceById(1L)).thenReturn(hall);

        List<SitDto> result = hallsService.getAllSitsInHall(1L);
        Assertions.assertEquals(1L, result.size());
        Assertions.assertEquals(sit.getId(), result.get(0).getId());
        Assertions.assertEquals(sit.getRow(), result.get(0).getRow());
        Assertions.assertEquals(sit.getPlace(), result.get(0).getPlace());
        Assertions.assertEquals(sit.getType().getId(), result.get(0).getType().getId());
        Assertions.assertEquals(sit.getType().getName(), result.get(0).getType().getName());
        Assertions.assertEquals(sit.getType().getCapacity(), result.get(0).getType().getCapacity());
        Assertions.assertEquals(sit.getType().getDescription(), result.get(0).getType().getDescription());
    }

    @Test
    public void getAllSitsInNotExistedHallTest() {
        Mockito.when(hallsRepository.existsById(Mockito.anyLong())).thenReturn(false);

        Assertions.assertThrows(HallNotFoundException.class, () -> hallsService.getAllSitsInHall(1L));
    }

    @Test
    public void getAllSitsInHallWithNullableHallIdTest() {
        Mockito.when(hallsRepository.existsById(Mockito.anyLong())).thenThrow(NullPointerException.class);

        Assertions.assertThrows(HallNotFoundException.class, () -> hallsService.getAllSitsInHall(null));
    }

}
