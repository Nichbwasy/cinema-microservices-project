package com.cinema.cinemas.microservice.controllers;

import com.cinema.cinemas.microservice.domains.CinemaDto;
import com.cinema.cinemas.microservice.domains.HallDto;
import com.cinema.cinemas.microservice.models.Cinema;
import com.cinema.cinemas.microservice.models.Hall;
import com.cinema.cinemas.microservice.services.CinemasService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cinemas")
public class CinemaController {

    @Autowired
    private CinemasService cinemasService;

    @GetMapping
    public ResponseEntity<List<CinemaDto>> getAllCinemas() {
        List<CinemaDto> cinemasDto = cinemasService.getAllCinemas();
        log.info("All '{}' cinemas has been returned.", cinemasDto.size());
        return ResponseEntity.ok().body(cinemasDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CinemaDto> getCinema(@PathVariable Long id) {
        CinemaDto cinemaDto = cinemasService.getCinema(id);
        log.info("Cinema has been returned: [{}].", cinemaDto);
        return ResponseEntity.ok().body(cinemaDto);
    }

    @PostMapping()
    public ResponseEntity<Long> createCinema(@ModelAttribute @Valid Cinema cinema) {
        CinemaDto cinemaDto = cinemasService.createCinema(cinema);
        log.info("A new cinema has been created successfully: [{}].", cinemaDto);
        return ResponseEntity.ok().body(cinemaDto.getId());
    }

    @PutMapping
    public ResponseEntity<Long> updatedCinema(@ModelAttribute @Valid Cinema cinema) {
        CinemaDto cinemaDto = cinemasService.updateCinema(cinema);
        log.info("A new cinema has been updated successfully: [{}].", cinemaDto);
        return ResponseEntity.ok().body(cinemaDto.getId());
    }

    @DeleteMapping
    public ResponseEntity<List<Long>> deleteCinemas(@RequestParam List<Long> ids) {
        List<Long> deletedIds = cinemasService.deleteCinemasByIds(ids);
        log.info("All cinemas with ids '{}' were deleted successfully", deletedIds);
        return ResponseEntity.ok().body(deletedIds);
    }

    @PostMapping("/{cinemaId}")
    public ResponseEntity<Long> addHallToTheCinema(@PathVariable Long cinemaId,
                                                   @ModelAttribute @Valid Hall hall,
                                                   @RequestParam Long sitsTypeId)
    {
        HallDto hallDto = cinemasService.addHall(cinemaId, sitsTypeId, hall);
        log.info("New hall has been added to the cinema with id '{}' successfully: [{}].", cinemaId, hallDto);
        return ResponseEntity.ok().body(hallDto.getId());
    }

}
