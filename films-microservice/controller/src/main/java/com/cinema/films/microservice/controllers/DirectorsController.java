package com.cinema.films.microservice.controllers;

import com.cinema.films.microservice.domains.DirectorDto;
import com.cinema.films.microservice.models.Director;
import com.cinema.films.microservice.services.DirectorsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films/directors")
public class DirectorsController {

    @Autowired
    private DirectorsService directorsService;

    @GetMapping
    public ResponseEntity<List<DirectorDto>> getAllDirectors() {
        List<DirectorDto> directorsDto = directorsService.getAllDirectors();
        log.info("All '{}' directors has been returned.", directorsDto.size());
        return ResponseEntity.ok().body(directorsDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DirectorDto> getDirector(@PathVariable Long id) {
        DirectorDto directorDto = directorsService.getDirector(id);
        log.info("Director with id '{}' has been returned.", id);
        return ResponseEntity.ok().body(directorDto);
    }

    @PostMapping
    public ResponseEntity<Long> createDirector(@ModelAttribute @Valid Director genre) {
        DirectorDto directorDto = directorsService.createDirector(genre);
        log.info("New director has been created: {}", directorDto);
        return ResponseEntity.ok().body(directorDto.getId());
    }

    @PutMapping
    public ResponseEntity<Long> updateDirector(@ModelAttribute @Valid Director genre) {
        DirectorDto directorDto = directorsService.updateDirector(genre);
        log.info("Director with id '{}' has been updated: {}", directorDto.getId(), directorDto);
        return ResponseEntity.ok().body(directorDto.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteDirector(@PathVariable Long id) {
        Long deletedId = directorsService.deleteDirector(id);
        log.info("Director with id '{}' has been deleted.", deletedId);
        return ResponseEntity.ok().body(deletedId);
    }

}
