package com.cinema.films.microservice.services.impliments;

import com.cinema.films.microservice.domains.DirectorDto;
import com.cinema.films.microservice.exceptions.services.directors.*;
import com.cinema.films.microservice.exceptions.services.genres.GenreAlreadyExistException;
import com.cinema.films.microservice.exceptions.services.genres.GenreCreationException;
import com.cinema.films.microservice.exceptions.services.genres.GenreNotFoundException;
import com.cinema.films.microservice.exceptions.services.genres.GenreUpdatingException;
import com.cinema.films.microservice.models.Director;
import com.cinema.films.microservice.models.Genre;
import com.cinema.films.microservice.repositories.DirectorsRepository;
import com.cinema.films.microservice.repositories.GenresRepository;
import com.cinema.films.microservice.services.DirectorsService;
import com.cinema.films.microservice.services.mappers.DirectorMapper;
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
public class DirectorsServiceImpl implements DirectorsService {

    @Autowired
    private DirectorsRepository directorsRepository;

    @Override
    public List<DirectorDto> getAllDirectors() {
        List<Director> directors = directorsRepository.findAll();
        log.info("{} directors has been found.", directors.size());
        return directors.stream().map(DirectorMapper.INSTANCE::mapToDto).collect(Collectors.toList());
    }

    @Override
    public DirectorDto getDirector(Long id) throws DirectorNotFoundException {
        if (directorsRepository.existsById(id)) {
            log.info("Director with id '{}' has been found.", id);
            return DirectorMapper.INSTANCE.mapToDto(directorsRepository.getReferenceById(id));
        } else {
            log.warn("Can't find director with id '{}'", id);
            throw new DirectorNotFoundException(String.format("Can't find director with id '%s'", id));
        }
    }

    @Override
    public DirectorDto createDirector(Director director) throws DirectorCreationException {
        if (!directorsRepository.existsByFirstNameAndLastName(director.getFirstName(), director.getLastName())) {
            try {
                Director createdDirector = directorsRepository.save(director);
                log.info("New director with id '{}' has been created.", createdDirector.getId());
                return DirectorMapper.INSTANCE.mapToDto(createdDirector);
            } catch (Exception e) {
                log.error("Error was occurred while creation a new director! " + e.getMessage());
                throw new DirectorCreationException("Error was occurred while creation a new director! " + e.getMessage());
            }
        } else {
            log.warn("Can't create director! Director '{} {}' already exists!", director.getFirstName(), director.getLastName());
            throw new DirectorAlreadyExistException(
                    String.format("Can't create director! Director with name '%s %s' already exists!", director.getFirstName(), director.getLastName()));
        }
    }

    @Override
    public DirectorDto updateDirector(Director director) throws DirectorNotFoundException, DirectorUpdatingException {
        if (directorsRepository.existsByFirstNameAndLastName(director.getFirstName(), director.getLastName())) {
            try {
                Director updatedDirector = directorsRepository.save(director);
                log.info("Director with id '{}' has been updated.", updatedDirector.getId());
                return DirectorMapper.INSTANCE.mapToDto(updatedDirector);
            } catch (Exception e) {
                log.error("Error was occurred while updating director! " + e.getMessage());
                throw new DirectorUpdatingException("Error was occurred while updating director! " + e.getMessage());
            }
        } else {
            log.warn("Can't update director! Director with id '{}' not found!", director.getId());
            throw new DirectorNotFoundException(
                    String.format("Can't update director! Director with id '{}' not found!", director.getId()));
        }
    }

    @Override
    public Long deleteDirector(Long id) throws DirectorNotFoundException, DirectorDeletingException {
        if (directorsRepository.existsById(id)) {
            try {
                directorsRepository.deleteById(id);
                log.info("Director with id '{}' has been deleted.", id);
                return id;
            } catch (Exception e) {
                log.error("Error was occurred while deleting director! " + e.getMessage());
                throw new DirectorDeletingException("Error was occurred while deleting director! " + e.getMessage());
            }
        } else {
            log.warn("Can't delete director! Director with id '{}' not exists!", id);
            throw new DirectorNotFoundException(
                    String.format("Can't delete director! Director with id '%d' not exists!", id));
        }
    }
}
