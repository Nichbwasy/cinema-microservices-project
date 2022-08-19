package com.cinema.cinemas.microservice.services;

import com.cinema.cinemas.microservice.domains.SitDto;
import com.cinema.cinemas.microservice.exceptions.services.sits.SitCreationException;
import com.cinema.cinemas.microservice.exceptions.services.sits.SitDeletingException;
import com.cinema.cinemas.microservice.exceptions.services.sits.SitNotFoundException;
import com.cinema.cinemas.microservice.exceptions.services.sits.SitUpdatingException;
import com.cinema.cinemas.microservice.models.Sit;
import com.cinema.cinemas.microservice.models.SitType;
import com.cinema.cinemas.microservice.repositories.SitsRepository;
import com.cinema.cinemas.microservice.services.configs.SitsServiceTestsConfiguration;
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

@SpringBootTest(classes = SitsService.class)
@Import(SitsServiceTestsConfiguration.class)
@ExtendWith(SpringExtension.class)
public class SitsServiceTests {

    private Sit sit = new Sit(1L, 1, 1, null);
    private SitType sitType = new SitType(1L, "name", "description", 1);

    @Autowired
    private SitsService sitsService;

    @MockBean
    private SitsRepository sitsRepository;

    @BeforeEach
    public void init() {
        sit.setType(sitType);
    }

    @Test
    public void getSitTest() {
        Mockito.when(sitsRepository.existsById(1L)).thenReturn(true);
        Mockito.when(sitsRepository.getReferenceById(1L)).thenReturn(sit);

        SitDto result = sitsService.getSit(1L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(sit.getId(), result.getId());
        Assertions.assertEquals(sit.getRow(), result.getRow());
        Assertions.assertEquals(sit.getPlace(), result.getPlace());
        Assertions.assertEquals(sit.getType().getId(), result.getType().getId());
        Assertions.assertEquals(sit.getType().getName(), result.getType().getName());
        Assertions.assertEquals(sit.getType().getCapacity(), result.getType().getCapacity());
        Assertions.assertEquals(sit.getType().getDescription(), result.getType().getDescription());
    }

    @Test
    public void getNotExistedSitTest() {
        Mockito.when(sitsRepository.existsById(Mockito.anyLong())).thenReturn(false);

        Assertions.assertThrows(SitNotFoundException.class, () -> sitsService.getSit(1L));
    }

    @Test
    public void getSitWithNullSitIdTest() {
        Mockito.when(sitsRepository.existsById(null)).thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class, () -> sitsService.getSit(null));
    }

    @Test
    public void createSitTest() {
        Mockito.when(sitsRepository.save(sit)).thenReturn(sit);

        SitDto result = sitsService.createSit(sit);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(sit.getId(), result.getId());
        Assertions.assertEquals(sit.getRow(), result.getRow());
        Assertions.assertEquals(sit.getPlace(), result.getPlace());
        Assertions.assertEquals(sit.getType().getId(), result.getType().getId());
        Assertions.assertEquals(sit.getType().getName(), result.getType().getName());
        Assertions.assertEquals(sit.getType().getCapacity(), result.getType().getCapacity());
        Assertions.assertEquals(sit.getType().getDescription(), result.getType().getDescription());
    }

    @Test
    public void createSitWitRepositoryExceptionTest() {
        Mockito.when(sitsRepository.save(Mockito.any(Sit.class))).thenThrow(RuntimeException.class);

        Assertions.assertThrows(SitCreationException.class, () -> sitsService.createSit(sit));
    }

    @Test
    public void createSitWithNullableDataTest() {
        Mockito.when(sitsRepository.save(null)).thenThrow(NullPointerException.class);

        Assertions.assertThrows(SitCreationException.class, () -> sitsService.createSit(sit));
    }

    @Test
    public void updateSitTest() {
        Mockito.when(sitsRepository.existsById(1L)).thenReturn(true);
        Mockito.when(sitsRepository.save(sit)).thenReturn(sit);

        SitDto result = sitsService.updateSit(sit);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(sit.getId(), result.getId());
        Assertions.assertEquals(sit.getRow(), result.getRow());
        Assertions.assertEquals(sit.getPlace(), result.getPlace());
        Assertions.assertEquals(sit.getType().getId(), result.getType().getId());
        Assertions.assertEquals(sit.getType().getName(), result.getType().getName());
        Assertions.assertEquals(sit.getType().getCapacity(), result.getType().getCapacity());
        Assertions.assertEquals(sit.getType().getDescription(), result.getType().getDescription());
    }

    @Test
    public void updateNotExistedSitTest() {
        Mockito.when(sitsRepository.existsById(Mockito.anyLong())).thenReturn(false);

        Assertions.assertThrows(SitNotFoundException.class, () -> sitsService.updateSit(sit));
    }

    @Test
    public void updateSitWithRepositoryExceptionTest() {
        Mockito.when(sitsRepository.existsById(1L)).thenReturn(true);
        Mockito.when(sitsRepository.save(Mockito.any(Sit.class))).thenThrow(RuntimeException.class);

        Assertions.assertThrows(SitUpdatingException.class, () -> sitsService.updateSit(sit));
    }

    @Test
    public void updateSitWithNullableSitDataTest() {
        Mockito.when(sitsRepository.existsById(null)).thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class, () -> sitsService.updateSit(null));
    }

    @Test
    public void deleteSitTest() {
        Mockito.when(sitsRepository.existsById(1L)).thenReturn(true);
        Mockito.doNothing().when(sitsRepository).deleteById(1L);

        Long result = sitsService.deleteSit(1L);
        Assertions.assertEquals(1L, result);
    }

    @Test
    public void deleteNotExistedSitTest() {
        Mockito.when(sitsRepository.existsById(Mockito.anyLong())).thenReturn(false);

        Assertions.assertThrows(SitNotFoundException.class, () -> sitsService.deleteSit(1L));
    }

    @Test
    public void deleteSitWithRepositoryExceptionTest() {
        Mockito.when(sitsRepository.existsById(1L)).thenReturn(true);
        Mockito.doThrow(RuntimeException.class).when(sitsRepository).deleteById(1L);

        Assertions.assertThrows(SitDeletingException.class, () -> sitsService.deleteSit(1L));
    }

    @Test
    public void deleteSitWithNullableSitIdTest() {
        Mockito.when(sitsRepository.existsById(null)).thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class, () -> sitsService.deleteSit(null));
    }

}
