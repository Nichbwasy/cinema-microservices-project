package com.cinema.films.microservice.services;

import com.cinema.films.microservice.domains.FilmDto;
import com.cinema.films.microservice.domains.FilmImgResourceDto;
import com.cinema.films.microservice.exceptions.services.films.*;
import com.cinema.films.microservice.models.Film;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface FilmsService {

    /**
     * Returns all films from repository.
     * @return List of films DTO's
     */
    List<FilmDto> getAllFilms();

    /**
     * Returns page of films from repository.
     * @param page Page of film.
     * @return List of existed films.
     *         If page not exist or no films in repository returns empty list.
     */
    List<FilmDto> getPageOfFilms(Integer page);

    /**
     * Returns film by id from repository.
     * @param id Id of film.
     * @return DTO of film.
     * @throws FilmNotFoundException Throws when film with selected id not found in repository.
     */
    FilmDto getFilm(Long id) throws FilmNotFoundException;

    /**
     * Creates a new film in repository.
     * @param film Film data.
     * @param imgFile Img poster file (png, jpg/jpeg extension) to save in resource storage.
     * @return DTO of saved film.
     * @throws FilmAlreadyExistException Throws when film with selected name already exist in repository.
     * @throws FilmCreationException Throws if any exception occurred while creating a new film.
     */
    FilmDto saveFilm(Film film, MultipartFile imgFile) throws FilmAlreadyExistException, FilmCreationException;

    /**
     * Updates a film data in repository.
     * @param film Updated film data.
     * @param imgFile A new img poster file (png, jpg/jpeg extension) to save in resource storage.
     * @return DTO of updated film.
     * @throws FilmNotFoundException Throws when film with selected id not found in repository.
     * @throws FilmAlreadyExistException Throws when film with selected name already exist in repository.
     * @throws FilmUpdatingException Throws if any exception occurred while updating a film.
     */
    FilmDto updateFilm(Film film, MultipartFile imgFile) throws FilmNotFoundException, FilmAlreadyExistException, FilmUpdatingException;

    /**
     * Returns poster metadata for selected film.
     * @param id Id of film.
     * @return DTO of poster metadata.
     * @throws FilmNotFoundException Throws when film with selected id not found in repository.
     */
    FilmImgResourceDto getFilmPosterMetadata(Long id) throws FilmNotFoundException;

    /**
     * Returns film poster img from resource storage by film id.
     * @param filmId Id of film.
     * @return Stream of poster img data.
     * @throws FilmNotFoundException Throws when film with selected id not found in repository.
     */
    InputStream getFilmPoster(Long filmId) throws FilmNotFoundException;

    /**
     * Removes film from repository by id.
     * @param id Id of film.
     * @return Id of removed film.
     * @throws FilmNotFoundException Throws when film with selected id not found in repository.
     * @throws FilmDeletingException Throws if any exception occurred while deleting a film.
     */
    Long deleteFilm(Long id) throws FilmNotFoundException, FilmDeletingException;
}
