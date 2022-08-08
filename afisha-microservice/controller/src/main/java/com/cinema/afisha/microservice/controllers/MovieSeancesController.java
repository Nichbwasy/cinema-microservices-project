package com.cinema.afisha.microservice.controllers;


import com.cinema.afisha.microservice.domains.MovieSeanceDto;
import com.cinema.afisha.microservice.models.MovieSeance;
import com.cinema.afisha.microservice.services.MovieSeancesService;
import com.cinema.afisha.microservice.services.dto.SeanceDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/afisha")
public class MovieSeancesController {

    @Autowired
    private MovieSeancesService movieSeancesService;

    @GetMapping("/page")
    public ResponseEntity<List<SeanceDto>> getSeancesPage(@PathVariable Integer page) {
        List<SeanceDto> seancesDto = movieSeancesService.getPageOfSeances(page);
        log.info("'{}' seances were return.", seancesDto.size());
        return ResponseEntity.ok().body(seancesDto);
    }

    @GetMapping("/seance/{id}")
    public ResponseEntity<SeanceDto> getSeance(@PathVariable Long id) {
        SeanceDto seanceDto = movieSeancesService.getSeance(id);
        log.info("Seance with id '{}' on film '{}' has been found.", id, seanceDto.getFilm().getName());
        return ResponseEntity.ok().body(seanceDto);
    }

    @PostMapping
    public ResponseEntity<MovieSeanceDto> createSeance(MovieSeance movieSeance) {
        MovieSeanceDto seanceDto = movieSeancesService.createSeance(movieSeance);
        log.info("New movie seance has been created: {}", seanceDto);
        return ResponseEntity.ok().body(seanceDto);
    }

    @PutMapping
    public ResponseEntity<MovieSeanceDto> updateSeance(MovieSeance movieSeance) {
        MovieSeanceDto seanceDto = movieSeancesService.updateSeance(movieSeance);
        log.info("Movie seance has been updated: {}", seanceDto);
        return ResponseEntity.ok().body(seanceDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteSeance(@PathVariable Long id) {
        Long deletedId = movieSeancesService.deleteSeance(id);
        log.info("Seance with id '{}' has been deleted successfully.", deletedId);
        return ResponseEntity.ok().body(deletedId);
    }

}
