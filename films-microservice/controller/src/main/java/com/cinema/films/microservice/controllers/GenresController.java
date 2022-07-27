package com.cinema.films.microservice.controllers;

import com.cinema.films.microservice.domains.GenreDto;
import com.cinema.films.microservice.models.Genre;
import com.cinema.films.microservice.services.GenresService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films/genres")
public class GenresController {

    @Autowired
    private GenresService genresService;

    @GetMapping
    public ResponseEntity<List<GenreDto>> getAllGenres() {
        List<GenreDto> genresDto = genresService.getAllGenres();
        log.info("All '{}' genres has been returned.", genresDto.size());
        return ResponseEntity.ok().body(genresDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreDto> getGenre(@PathVariable Long id) {
        GenreDto genreDto = genresService.getGenre(id);
        log.info("Genre with id '{}' has been returned.", id);
        return ResponseEntity.ok().body(genreDto);
    }

    @PostMapping
    public ResponseEntity<Long> createGenre(@ModelAttribute @Valid Genre genre) {
        GenreDto genreDto = genresService.createGenre(genre);
        log.info("New genre has been created: {}", genreDto);
        return ResponseEntity.ok().body(genreDto.getId());
    }

    @PutMapping
    public ResponseEntity<Long> updateGenre(@ModelAttribute @Valid Genre genre) {
        GenreDto genreDto = genresService.updateGenre(genre);
        log.info("Genre with id '{}' has been updated: {}", genreDto.getId(), genreDto);
        return ResponseEntity.ok().body(genreDto.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteGenre(@PathVariable Long id) {
        Long deletedId = genresService.deleteGenre(id);
        log.info("Genre with id '{}' has been deleted.", deletedId);
        return ResponseEntity.ok().body(deletedId);
    }

}
