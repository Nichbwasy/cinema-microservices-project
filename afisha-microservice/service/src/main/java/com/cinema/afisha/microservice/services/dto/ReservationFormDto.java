package com.cinema.afisha.microservice.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationFormDto {
    private Long id;
    private String reserverEmail;
    private Date reservationTime;
    private Long movieSeanceId;
    private Long sitId;
}
