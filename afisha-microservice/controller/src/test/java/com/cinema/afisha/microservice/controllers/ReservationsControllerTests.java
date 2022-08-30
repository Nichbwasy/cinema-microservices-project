package com.cinema.afisha.microservice.controllers;

import com.cinema.afisha.microservice.controllers.configs.AfishaControllersTestConfiguration;
import com.cinema.afisha.microservice.domains.ReservationDto;
import com.cinema.afisha.microservice.exceptions.services.reservations.ReservationAlreadyExistException;
import com.cinema.afisha.microservice.exceptions.services.reservations.ReservationCreationException;
import com.cinema.afisha.microservice.exceptions.services.reservations.ReservationDeletingException;
import com.cinema.afisha.microservice.exceptions.services.reservations.ReservationNotFoundException;
import com.cinema.afisha.microservice.exceptions.services.seances.MovieSeanceNotFoundException;
import com.cinema.afisha.microservice.exceptions.services.seances.sits.MovieSeanceSitNotFoundException;
import com.cinema.afisha.microservice.models.Reservation;
import com.cinema.afisha.microservice.services.ReservationsService;
import com.cinema.afisha.microservice.services.dto.ReservationFormDto;
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

import java.util.Date;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {ReservationsController.class})
@Import(AfishaControllersTestConfiguration.class)
@AutoConfigureMockMvc( addFilters = false ) // disable security
public class ReservationsControllerTests {

    private static final Reservation reservation = new Reservation(1L, "mail@mail.com", new Date(), null, null);
    private static final ReservationDto reservationDto = new ReservationDto(1L, "mail@mail.com", new Date(), null, null);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationsService reservationsService;

    @Test
    public void createReservationTest() throws Exception {
        ReservationFormDto reservationFormDto = new ReservationFormDto(1L, "mail@mail.com", new Date(), 1L, 1L);

        Mockito.when(reservationsService.createReservation(Mockito.any(ReservationFormDto.class))).thenReturn(reservationDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                    .post("/afisha/seance/1/reservation")
                    .flashAttr("reservation", reservationFormDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(reservationDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reserverEmail").value(reservationDto.getReserverEmail()));
    }

    @Test
    public void createReservationWithMovieSeanceNotFoundExceptionTest() throws Exception {
        ReservationFormDto reservationFormDto = new ReservationFormDto(1L, "mail@mail.com", new Date(), 1L, 1L);

        Mockito.when(reservationsService.createReservation(Mockito.any(ReservationFormDto.class))).thenThrow(MovieSeanceNotFoundException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/afisha/seance/1/reservation")
                        .flashAttr("reservation", reservationFormDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createReservationWithMovieSeanceSitNotFoundExceptionTest() throws Exception {
        ReservationFormDto reservationFormDto = new ReservationFormDto(1L, "mail@mail.com", new Date(), 1L, 1L);

        Mockito.when(reservationsService.createReservation(Mockito.any(ReservationFormDto.class))).thenThrow(MovieSeanceSitNotFoundException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/afisha/seance/1/reservation")
                        .flashAttr("reservation", reservationFormDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createReservationWithReservationAlreadyExistExceptionTest() throws Exception {
        ReservationFormDto reservationFormDto = new ReservationFormDto(1L, "mail@mail.com", new Date(), 1L, 1L);

        Mockito.when(reservationsService.createReservation(Mockito.any(ReservationFormDto.class))).thenThrow(ReservationAlreadyExistException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/afisha/seance/1/reservation")
                        .flashAttr("reservation", reservationFormDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createReservationWithServiceExceptionTest() throws Exception {
        ReservationFormDto reservationFormDto = new ReservationFormDto(1L, "mail@mail.com", new Date(), 1L, 1L);

        Mockito.when(reservationsService.createReservation(Mockito.any(ReservationFormDto.class))).thenThrow(ReservationCreationException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/afisha/seance/1/reservation")
                        .flashAttr("reservation", reservationFormDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteReservationTest() throws Exception {
        Mockito.when(reservationsService.deleteReservation(Mockito.anyLong())).thenAnswer(a -> a.getArguments()[0]);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/afisha/seance/1/reservation/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(1));
    }

    @Test
    public void deleteNotExistedReservationTest() throws Exception {
        Mockito.when(reservationsService.deleteReservation(Mockito.anyLong())).thenThrow(ReservationNotFoundException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/afisha/seance/1/reservation/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteReservationWithServiceExceptionTest() throws Exception {
        Mockito.when(reservationsService.deleteReservation(Mockito.anyLong())).thenThrow(ReservationDeletingException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/afisha/seance/1/reservation/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

}
