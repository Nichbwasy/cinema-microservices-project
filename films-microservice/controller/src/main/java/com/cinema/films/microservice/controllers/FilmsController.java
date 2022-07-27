package com.cinema.films.microservice.controllers;

import com.cinema.films.microservice.domains.FilmDto;
import com.cinema.films.microservice.models.Film;
import com.cinema.films.microservice.services.FilmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmsController {

    @Autowired
    private FilmsService filmsService;

    @GetMapping
    public ResponseEntity<List<FilmDto>> getAllFilms() {
        List<FilmDto> films = filmsService.getAllFilms();
        log.info("All '{}' films have been returned.", films.size());
        return ResponseEntity.ok().body(films);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmDto> getFilm(@PathVariable Long id) {
        FilmDto filmDto = filmsService.getFilm(id);
        log.info("Film with id '{}' has been found: {}", id , filmDto);
        return ResponseEntity.ok().body(filmDto);
    }

    @GetMapping("/catalog/{page}")
    public ResponseEntity<List<FilmDto>> getFilmsPage(@PathVariable Integer page) {
        List<FilmDto> films = filmsService.getPageOfFilms(page - 1);
        log.info("{} page with '{}' films have been returned.", page, films.size());
        return ResponseEntity.ok().body(films);
    }

    @PostMapping
    public ResponseEntity<Long> createFilm(@ModelAttribute @Valid Film film, @ModelAttribute MultipartFile img) {
        FilmDto filmDto = filmsService.saveFilm(film, img);
        log.info("New film has been created: {}", filmDto);
        return ResponseEntity.ok().body(filmDto.getId());
    }

    @PutMapping
    public ResponseEntity<Long> update(@ModelAttribute @Valid Film film, @ModelAttribute MultipartFile img) {
        FilmDto filmDto = filmsService.updateFilm(film, img);
        log.info("Film has been updated: {}", filmDto);
        return ResponseEntity.ok().body(filmDto.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteFilm(@PathVariable Long id) {
        Long deletedId = filmsService.deleteFilm(id);
        log.info("Film with id '{}' has been deleted successfully.", deletedId);
        return ResponseEntity.ok().body(deletedId);
    }

}
