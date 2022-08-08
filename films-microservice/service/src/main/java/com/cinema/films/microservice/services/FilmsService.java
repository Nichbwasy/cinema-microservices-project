package com.cinema.films.microservice.services;

import com.cinema.films.microservice.domains.FilmDto;
import com.cinema.films.microservice.domains.FilmImgResourceDto;
import com.cinema.films.microservice.models.Film;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface FilmsService {
    List<FilmDto> getAllFilms();
    List<FilmDto> getPageOfFilms(Integer page);
    FilmDto getFilm(Long id);
    FilmDto saveFilm(Film film, MultipartFile imgFile);
    FilmDto updateFilm(Film film, MultipartFile imgFile);
    FilmImgResourceDto getFilmMetadata(Long id);
    InputStream getFilmPoster(Long filmId);
    Long deleteFilm(Long id);
}
