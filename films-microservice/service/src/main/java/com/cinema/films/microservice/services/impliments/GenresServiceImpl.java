package com.cinema.films.microservice.services.impliments;

import com.cinema.films.microservice.domains.GenreDto;
import com.cinema.films.microservice.exceptions.services.genres.*;
import com.cinema.films.microservice.models.Genre;
import com.cinema.films.microservice.repositories.GenresRepository;
import com.cinema.films.microservice.services.GenresService;
import com.cinema.films.microservice.services.mappers.GenreMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class GenresServiceImpl implements GenresService {

    @Autowired
    private GenresRepository genresRepository;

    @Override
    public List<GenreDto> getAllGenres() {
        List<Genre> genres = genresRepository.findAll();
        log.info("{} genres has been found.", genres.size());
        return genres.stream().map(GenreMapper.INSTANCE::mapToDto).collect(Collectors.toList());
    }

    @Override
    public GenreDto getGenre(Long id) throws GenreNotFoundException {
        if (genresRepository.existsById(id)) {
            log.info("Genre with id '{}' has been found.", id);
            return GenreMapper.INSTANCE.mapToDto(genresRepository.getReferenceById(id));
        } else {
            log.warn("Can't find genre with id '{}'", id);
            throw new GenreNotFoundException(String.format("Can't find genre with id '%s'", id));
        }
    }

    @Override
    public GenreDto createGenre(Genre genre) throws GenreAlreadyExistException, GenreCreationException {
        if (!genresRepository.existsByName(genre.getName())) {
            try {
                Genre createdGenre = genresRepository.save(genre);
                log.info("New genre with id '{}' has been created.", createdGenre.getId());
                return GenreMapper.INSTANCE.mapToDto(createdGenre);
            } catch (Exception e) {
                log.error("Error was occurred while creation a new genre! " + e.getMessage());
                throw new GenreCreationException("Error was occurred while creation a new genre! " + e.getMessage());
            }
        } else {
            log.warn("Can't create genre! Genre with name '{}' already exists!", genre.getName());
            throw new GenreAlreadyExistException(
                    String.format("Can't create genre! Genre with name '%s' already exists!", genre.getName()));
        }
    }

    @Override
    public GenreDto updateGenre(Genre genre) throws GenreNotFoundException, GenreUpdatingException {
        if (genresRepository.existsById(genre.getId())) {
            try {
                Genre updatedGenre = genresRepository.save(genre);
                log.info("Genre with id '{}' has been updated.", updatedGenre.getId());
                return GenreMapper.INSTANCE.mapToDto(updatedGenre);
            } catch (Exception e) {
                log.error("Error was occurred while updating genre! " + e.getMessage());
                throw new GenreUpdatingException("Error was occurred while updating genre! " + e.getMessage());
            }
        } else {
            log.warn("Can't update genre! Genre type with id '{}' not exists!", genre.getId());
            throw new GenreNotFoundException(
                    String.format("Can't update genre! Genre type with id '%d' not exists!", genre.getId()));
        }
    }

    @Override
    public Long deleteGenre(Long id) throws GenreNotFoundException, GenreDeletingException {
        if (genresRepository.existsById(id)) {
            try {
                genresRepository.deleteById(id);
                log.info("Genre with id '{}' has been deleted.", id);
                return id;
            } catch (Exception e) {
                log.error("Error was occurred while deleting genre! " + e.getMessage());
                throw new GenreDeletingException("Error was occurred while deleting genre! " + e.getMessage());
            }
        } else {
            log.warn("Can't delete genre! Genre with id '{}' not exists!", id);
            throw new GenreNotFoundException(
                    String.format("Can't delete genre! Genre with id '%d' not exists!", id));
        }
    }
}
