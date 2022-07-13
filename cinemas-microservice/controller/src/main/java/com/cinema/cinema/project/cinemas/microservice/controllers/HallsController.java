package com.cinema.cinema.project.cinemas.microservice.controllers;

import com.cinema.cinema.project.cinemas.microservice.domains.HallDto;
import com.cinema.cinema.project.cinemas.microservice.models.Hall;
import com.cinema.cinema.project.cinemas.microservice.services.HallsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cinemas/{cinemaId}/halls")
public class HallsController {

    @Autowired
    private HallsService hallsService;

    @GetMapping
    public ResponseEntity<List<HallDto>> getAllCinemaHalls(@PathVariable Long cinemaId) {
        List<HallDto> hallsDto = hallsService.getAllHallsInCinema(cinemaId);
        log.info("For the cinema with id '{}' has been returned '{}' halls.", cinemaId, hallsDto.size());
        return ResponseEntity.ok(hallsDto);
    }

    @GetMapping("/{hallId}")
    public ResponseEntity<HallDto> getHall(@PathVariable Long cinemaId, @PathVariable Long hallId) {
        HallDto hallDto = hallsService.getCinemaHall(cinemaId, hallId);
        log.info("Hall with id '{}' has been found in cinema with id '{}': [{}].", hallId, cinemaId, hallDto);
        return ResponseEntity.ok(hallDto);
    }

    @PostMapping
    public ResponseEntity<Long> createHall(@ModelAttribute @Valid Hall hall, @RequestParam Long sitTypeId) {
        HallDto hallDto = hallsService.createHall(sitTypeId, hall);
        log.info("New hall has been created successfully: [{}].", hallDto);
        return ResponseEntity.ok(hallDto.getId());
    }

    @PutMapping
    public ResponseEntity<Long> updateHall(@ModelAttribute @Valid Hall hall) {
        HallDto hallDto = hallsService.updateHall(hall);
        log.info("Hall has been updated successfully: [{}].", hallDto);
        return ResponseEntity.ok().body(hallDto.getId());
    }

    @DeleteMapping("/{hallId}")
    public ResponseEntity<Long> deletedHallFromCinema(@PathVariable Long cinemaId, @PathVariable Long hallId) {
        Long deletedId = hallsService.deleteHallFromCinema(cinemaId, hallId);
        log.info("Hall with id '{}' has been deleted from the cinema with id '{}' successfully.", deletedId, cinemaId);
        return ResponseEntity.ok().body(deletedId);
    }

    @DeleteMapping
    public ResponseEntity<List<Long>> deleteAllHallsFromCinema(@PathVariable Long cinemaId) {
        List<Long> deletedIds = hallsService.deleteAllHallsFromCinema(cinemaId);
        log.info("All halls with ids '{}' has been deleted from cinema with id '{}' successfully.", deletedIds, cinemaId);
        return ResponseEntity.ok().body(deletedIds);
    }

}
