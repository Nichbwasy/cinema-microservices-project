package com.cinema.cinemas.microservice.controllers;

import com.cinema.cinemas.microservice.controllers.configs.ControllersTestConfiguration;
import com.cinema.cinemas.microservice.domains.SitTypeDto;
import com.cinema.cinemas.microservice.exceptions.services.sits.types.*;
import com.cinema.cinemas.microservice.models.SitType;
import com.cinema.cinemas.microservice.services.SitsTypesService;
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
@WebMvcTest(controllers = {SitsTypesController.class})
@Import(ControllersTestConfiguration.class)
@AutoConfigureMockMvc( addFilters = false ) // disable security
public class SitTypesControllerTests {

    private SitType sitType = new SitType(1L, "sit_name", "description", 1);
    private SitTypeDto sitTypeDto = new SitTypeDto(1L, "sit_name", "description", 1);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SitsTypesService sitsTypesService;

    @Test
    public void getAllSitsTypesTest() throws Exception {
        Mockito.when(sitsTypesService.getAllSitsTypes()).thenReturn(Collections.singletonList(sitTypeDto));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/sits/types"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id", containsInAnyOrder(sitTypeDto.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name", containsInAnyOrder(sitTypeDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].capacity", containsInAnyOrder(sitTypeDto.getCapacity())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].description", containsInAnyOrder(sitTypeDto.getDescription())));
    }

    @Test
    public void getAllSitsTypesWithServiceExceptionTest() throws Exception {
        Mockito.when(sitsTypesService.getAllSitsTypes()).thenThrow(RuntimeException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/sits/types"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getSitTypeTest() throws Exception {
        Mockito.when(sitsTypesService.getSitType(1L)).thenReturn(sitTypeDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/sits/types/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(sitTypeDto.getId().intValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(sitTypeDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.capacity").value(sitTypeDto.getCapacity()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(sitTypeDto.getDescription()));

    }

    @Test
    public void getNotExistedSitTypeTest() throws Exception {
        Mockito.when(sitsTypesService.getSitType(1L)).thenThrow(SitTypeNotFoundException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/sits/types/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void getSitTypeWithServiceExceptionTest() throws Exception {
        Mockito.when(sitsTypesService.getSitType(Mockito.anyLong())).thenThrow(RuntimeException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/sits/types/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void createSitTypeTest() throws Exception {
        Mockito.when(sitsTypesService.createSitType(sitType)).thenReturn(sitTypeDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/sits/types")
                        .flashAttr("sitType", sitType))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(1));
    }

    @Test
    public void createAlreadyExistSitTypeTest() throws Exception {
        Mockito.when(sitsTypesService.createSitType(Mockito.any(SitType.class)))
                .thenThrow(SitTypeAlreadyExistException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/sits/types")
                        .flashAttr("sitType", sitType))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createSitTypeWithServiceExceptionTest() throws Exception {
        Mockito.when(sitsTypesService.createSitType(Mockito.any(SitType.class)))
                .thenThrow(SitTypeCreationException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/sits/types")
                        .flashAttr("sitType", sitType))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateSitTypeTest() throws Exception {
        Mockito.when(sitsTypesService.updateSitType(sitType))
                .thenReturn(sitTypeDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                    .put("/sits/types")
                    .flashAttr("sitType", sitType))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(1));

    }

    @Test
    public void updateNotExistedSitTypeTest() throws Exception {
        Mockito.when(sitsTypesService.updateSitType(sitType))
                .thenThrow(SitTypeNotFoundException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/sits/types")
                        .flashAttr("sitType", sitType))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void updateSitTypeWithServiceExceptionTest() throws Exception {
        Mockito.when(sitsTypesService.updateSitType(Mockito.any(SitType.class)))
                .thenThrow(SitTypeUpdatingException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/sits/types")
                        .flashAttr("sitType", sitType))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void deleteSitTypeTest() throws Exception {
        Mockito.when(sitsTypesService.deleteSitType(1L)).thenReturn(1L);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/sits/types/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(1));
    }

    @Test
    public void deleteNotExistedSitTypeTest() throws Exception {
        Mockito.when(sitsTypesService.deleteSitType(1L)).thenThrow(SitTypeNotFoundException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/sits/types/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteSitTypeWithServiceExceptionTest() throws Exception {
        Mockito.when(sitsTypesService.deleteSitType(1L)).thenThrow(SitTypeDeletingException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/sits/types/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

}
