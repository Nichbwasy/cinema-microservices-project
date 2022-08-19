package com.cinema.cinemas.microservice.services;

import com.cinema.cinemas.microservice.domains.SitTypeDto;
import com.cinema.cinemas.microservice.exceptions.services.sits.types.*;
import com.cinema.cinemas.microservice.models.SitType;
import com.cinema.cinemas.microservice.repositories.SitsTypesRepository;
import com.cinema.cinemas.microservice.services.configs.SitTypesServiceTestsConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(classes = SitsTypesService.class)
@Import(SitTypesServiceTestsConfiguration.class)
@ExtendWith(SpringExtension.class)
public class SitTypesServiceTests {

    private SitType sitType = new SitType(1L, "name", "description", 1);

    @Autowired
    private SitsTypesService sitsTypesService;

    @MockBean
    private SitsTypesRepository sitsTypesRepository;

    @Test
    public void getAllSitsTypesTest() {
        Mockito.when(sitsTypesRepository.findAll()).thenReturn(Arrays.asList(sitType));

        List<SitTypeDto> result = sitsTypesService.getAllSitsTypes();
        Assertions.assertEquals(1L, result.size());
    }

    @Test
    public void getAllSitsTypesWithRepositoryExceptionTest() {
        Mockito.when(sitsTypesRepository.findAll()).thenThrow(RuntimeException.class);

        Assertions.assertThrows(RuntimeException.class, () -> sitsTypesService.getAllSitsTypes());
    }

    @Test
    public void getSitTypeTest() {
        Mockito.when(sitsTypesRepository.existsById(1L)).thenReturn(true);
        Mockito.when(sitsTypesRepository.getReferenceById(1L)).thenReturn(sitType);

        SitTypeDto result = sitsTypesService.getSitType(1L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(sitType.getId(), result.getId());
        Assertions.assertEquals(sitType.getName(), result.getName());
        Assertions.assertEquals(sitType.getCapacity(), result.getCapacity());
        Assertions.assertEquals(sitType.getDescription(), result.getDescription());
    }

    @Test
    public void getNotExistedSitTypeTest() {
        Mockito.when(sitsTypesRepository.existsById(Mockito.anyLong())).thenReturn(false);

        Assertions.assertThrows(SitTypeNotFoundException.class, () -> sitsTypesService.getSitType(1L));
    }

    @Test
    public void getSitTypeWithRepositoryExceptionTest() {
        Mockito.when(sitsTypesRepository.existsById(1L)).thenReturn(true);
        Mockito.when(sitsTypesRepository.getReferenceById(1L)).thenThrow(RuntimeException.class);

        Assertions.assertThrows(RuntimeException.class, () -> sitsTypesService.getSitType(1L));
    }

    @Test
    public void getSitTypeWithNullSitTypeIdTest() {
        Mockito.when(sitsTypesRepository.existsById(null)).thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class, () -> sitsTypesService.getSitType(null));
    }

    @Test
    public void createSitTypeTest() {
        Mockito.when(sitsTypesRepository.existsByName(Mockito.anyString())).thenReturn(false);
        Mockito.when(sitsTypesRepository.save(Mockito.any())).then(AdditionalAnswers.returnsFirstArg());

        SitTypeDto result = sitsTypesService.createSitType(sitType);
        Assertions.assertEquals(sitType.getId(), result.getId());
        Assertions.assertEquals(sitType.getName(), result.getName());
        Assertions.assertEquals(sitType.getCapacity(), result.getCapacity());
        Assertions.assertEquals(sitType.getDescription(), result.getDescription());
    }

    @Test
    public void createAlreadyExistedSitTypeTest() {
        Mockito.when(sitsTypesRepository.existsByName(Mockito.anyString())).thenReturn(true);

        Assertions.assertThrows(SitTypeAlreadyExistException.class, () -> sitsTypesService.createSitType(sitType));
    }

    @Test
    public void createSitTypeWithRepositoryExceptionTest() {
        Mockito.when(sitsTypesRepository.existsByName(Mockito.anyString())).thenReturn(false);
        Mockito.when(sitsTypesRepository.save(Mockito.any())).thenThrow(RuntimeException.class);

        Assertions.assertThrows(SitTypeCreationException.class, () -> sitsTypesService.createSitType(sitType));
    }

    @Test
    public void createSitTypeWithNullSitTypeDataTest() {
        Assertions.assertThrows(NullPointerException.class, () -> sitsTypesService.createSitType(null));
    }

    @Test
    public void updateSitTypeTest() {
        Mockito.when(sitsTypesRepository.existsById(1L)).thenReturn(true);
        Mockito.when(sitsTypesRepository.save(Mockito.any())).then(AdditionalAnswers.returnsFirstArg());

        SitTypeDto result = sitsTypesService.updateSitType(sitType);
        Assertions.assertEquals(sitType.getId(), result.getId());
        Assertions.assertEquals(sitType.getName(), result.getName());
        Assertions.assertEquals(sitType.getCapacity(), result.getCapacity());
        Assertions.assertEquals(sitType.getDescription(), result.getDescription());
    }

    @Test
    public void updateNotExistedSitTypeTest() {
        Mockito.when(sitsTypesRepository.existsById(1L)).thenReturn(false);

        Assertions.assertThrows(SitTypeNotFoundException.class, () -> sitsTypesService.updateSitType(sitType));
    }

    @Test
    public void updateSitTypeWithRepositoryExceptionTest() {
        Mockito.when(sitsTypesRepository.existsById(1L)).thenReturn(true);
        Mockito.when(sitsTypesRepository.save(Mockito.any())).thenThrow(RuntimeException.class);

        Assertions.assertThrows(SitTypeUpdatingException.class, () -> sitsTypesService.updateSitType(sitType));
    }

    @Test
    public void updateSitTypeWithNullDataTest() {
        Mockito.when(sitsTypesRepository.existsById(null)).thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class, () -> sitsTypesService.updateSitType(null));
    }

    @Test
    public void deleteSitTypeTest() {
        Mockito.when(sitsTypesRepository.existsById(1L)).thenReturn(true);
        Mockito.doNothing().when(sitsTypesRepository).deleteById(1L);

        Long result = sitsTypesService.deleteSitType(1L);
        Assertions.assertEquals(1L, result);
    }

    @Test
    public void deleteNotExistedSitTypeTest() {
        Mockito.when(sitsTypesRepository.existsById(1L)).thenReturn(false);

        Assertions.assertThrows(SitTypeNotFoundException.class, () -> sitsTypesService.deleteSitType(1L));
    }

    @Test
    public void deleteSitTypeWithRepositoryExceptionTest() {
        Mockito.when(sitsTypesRepository.existsById(1L)).thenReturn(true);
        Mockito.doThrow(RuntimeException.class).when(sitsTypesRepository).deleteById(1L);

        Assertions.assertThrows(SitTypeDeletingException.class, () -> sitsTypesService.deleteSitType(1L));
    }

    @Test
    public void deleteSitTypeWithNullSitTypeIdTest() {
        Mockito.when(sitsTypesRepository.existsById(null)).thenThrow(NullPointerException.class);

        Assertions.assertThrows(SitTypeNotFoundException.class, () -> sitsTypesService.deleteSitType(1L));
    }
}
