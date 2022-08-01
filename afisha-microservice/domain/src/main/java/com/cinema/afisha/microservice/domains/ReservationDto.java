package com.cinema.afisha.microservice.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    private Long id;
    private String reserverEmail;
    private Date reservationTime;
    private MovieSeanceDto movieSeance;
    private MovieSeanceSitDto sit;
}
