package com.cinema.afisha.microservice.services.mappers;

import com.cinema.afisha.microservice.domains.ReservationDto;
import com.cinema.afisha.microservice.models.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    ReservationDto mapToDto(Reservation reservation);

}
