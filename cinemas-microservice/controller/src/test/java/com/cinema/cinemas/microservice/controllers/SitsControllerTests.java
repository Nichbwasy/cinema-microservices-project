package com.cinema.cinemas.microservice.controllers;

import com.cinema.cinemas.microservice.controllers.configs.CinemasControllersTestConfiguration;
import com.cinema.cinemas.microservice.domains.SitDto;
import com.cinema.cinemas.microservice.domains.SitTypeDto;
import com.cinema.cinemas.microservice.exceptions.services.sits.SitCreationException;
import com.cinema.cinemas.microservice.exceptions.services.sits.SitDeletingException;
import com.cinema.cinemas.microservice.exceptions.services.sits.SitNotFoundException;
import com.cinema.cinemas.microservice.exceptions.services.sits.SitUpdatingException;
import com.cinema.cinemas.microservice.models.Sit;
import com.cinema.cinemas.microservice.models.SitType;
import com.cinema.cinemas.microservice.services.SitsService;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {SitsController.class})
@Import(CinemasControllersTestConfiguration.class)
@AutoConfigureMockMvc( addFilters = false ) // disable security
public class SitsControllerTests {

    private static Sit sit = new Sit(1L, 1, 1, null);
    private static SitDto sitDto = new SitDto(1L, 1, 1, null);
    private static SitType sitType = new SitType(1L, "sit_type_name", "description", 1);
    private static SitTypeDto sitTypeDto = new SitTypeDto(1L, "sit_type_name", "description", 1);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SitsService sitsService;


    @BeforeAll
    public static void init() {
        sit.setType(sitType);
        sitDto.setType(sitTypeDto);
    }

    @Test
    public void getSitTest() throws Exception {
        Mockito.when(sitsService.getSit(1L)).thenReturn(sitDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/sits/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(sit.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.row").value(sit.getRow()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.place").value(sit.getPlace()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type.id").value(sit.getType().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type.name").value(sit.getType().getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type.capacity").value(sit.getType().getCapacity()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type.description").value(sit.getType().getDescription()));
    }

    @Test
    public void getNotExistedSitTest() throws Exception {
        Mockito.when(sitsService.getSit(1L)).thenThrow(SitNotFoundException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/sits/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getSitWithServiceExceptionTest() throws Exception {
        Mockito.when(sitsService.getSit(1L)).thenThrow(RuntimeException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/sits/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createSitTest() throws Exception {
        Mockito.when(sitsService.createSit(sit)).thenReturn(sitDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/sits")
                        .flashAttr("sit", sit))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(sit.getId()));
    }

    @Test
    public void createSitWithServiceExceptionTest() throws Exception {
        Mockito.when(sitsService.createSit(sit)).thenThrow(SitCreationException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/sits")
                        .flashAttr("sit", sit))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateSitTest() throws Exception {
        Mockito.when(sitsService.updateSit(sit)).thenReturn(sitDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/sits")
                        .flashAttr("sit", sit))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(1));
    }

    @Test
    public void updateNotExistedSitTest() throws Exception {
        Mockito.when(sitsService.updateSit(sit)).thenThrow(SitNotFoundException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/sits")
                        .flashAttr("sit", sit))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateSitWithServiceExceptionTest() throws Exception {
        Mockito.when(sitsService.updateSit(sit)).thenThrow(SitUpdatingException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/sits")
                        .flashAttr("sit", sit))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteSitTest() throws Exception {
        Mockito.when(sitsService.deleteSit(1L)).thenReturn(1L);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/sits/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(1));
    }

    @Test
    public void deleteNotExistedSitTest() throws Exception {
        Mockito.when(sitsService.deleteSit(1L)).thenThrow(SitNotFoundException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/sits/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteSitWithServiceExceptionTest() throws Exception {
        Mockito.when(sitsService.deleteSit(1L)).thenThrow(SitDeletingException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/sits/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

}
