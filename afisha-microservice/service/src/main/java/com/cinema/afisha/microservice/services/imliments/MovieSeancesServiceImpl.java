package com.cinema.afisha.microservice.services.imliments;

import com.cinema.afisha.microservice.domains.MovieSeanceDto;
import com.cinema.afisha.microservice.exceptions.services.seances.MovieSeanceCreationException;
import com.cinema.afisha.microservice.exceptions.services.seances.MovieSeanceDeletingException;
import com.cinema.afisha.microservice.exceptions.services.seances.MovieSeanceNotFoundException;
import com.cinema.afisha.microservice.exceptions.services.seances.api.MovieSeanceApiException;
import com.cinema.afisha.microservice.models.MovieSeance;
import com.cinema.afisha.microservice.models.MovieSeanceSit;
import com.cinema.afisha.microservice.repositories.MovieSeanceSitsRepository;
import com.cinema.afisha.microservice.repositories.MovieSeancesRepository;
import com.cinema.afisha.microservice.services.MovieSeancesService;
import com.cinema.afisha.microservice.services.dto.SeanceDto;
import com.cinema.afisha.microservice.services.mappers.MovieSeanceMapper;
import com.cinema.cinemas.microservice.clients.CinemasApiClient;
import com.cinema.cinemas.microservice.domains.HallDto;
import com.cinema.cinemas.microservice.exceptions.services.cinemas.CinemaNotFoundException;
import com.cinema.cinemas.microservice.exceptions.services.halls.HallNotBelongCinemaException;
import com.cinema.common.utils.contants.movie.seances.SitsStatuses;
import com.cinema.common.utils.generators.StringGenerator;
import com.cinema.films.microservice.clients.FilmsApiClient;
import com.cinema.films.microservice.domains.FilmDto;
import com.cinema.films.microservice.exceptions.services.films.FilmNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class MovieSeancesServiceImpl implements MovieSeancesService {

    @Autowired
    private MovieSeancesRepository movieSeancesRepository;

    @Autowired
    private MovieSeanceSitsRepository movieSeanceSitsRepository;

    @Autowired
    private CinemasApiClient cinemasApiClient;

    @Autowired
    private FilmsApiClient filmsApiClient;

    @Value("${afisha.seances.page.size}")
    private Integer SEANCES_PAGE_SIZE;

    @Override
    public List<SeanceDto> getPageOfSeances(Integer page) {
        Pageable pageable = PageRequest.of(page, SEANCES_PAGE_SIZE);
        List<MovieSeance> seances = movieSeancesRepository.findAll(pageable).getContent();
        log.info("{} seances was found.", seances.size());
        List<SeanceDto> seancesDto = new ArrayList<>();
        seances.forEach(movieSeance -> {
            SeanceDto seanceDto = new SeanceDto();
            try {
                FilmDto filmDto = filmsApiClient.getFilm(movieSeance.getFilmId());
                HallDto hallDto = cinemasApiClient.getCinemaHall(movieSeance.getCinemaId(), movieSeance.getHallId());
                InputStream poster = filmsApiClient.getFilmPoster(movieSeance.getFilmId());

                seanceDto.setMovieSeance(MovieSeanceMapper.INSTANCE.mapToDto(movieSeance));
                seanceDto.setFilm(filmDto);
                seanceDto.setHall(hallDto);
                seanceDto.setPoster(poster);

                seancesDto.add(seanceDto);
            } catch (Exception e) {
                log.error("Can't execute seance! " + e.getMessage());
            }
        });
        return seancesDto;
    }

    @Override
    public SeanceDto getSeance(Long id) {
        if (movieSeancesRepository.existsById(id)) {
            MovieSeance movieSeance = movieSeancesRepository.getReferenceById(id);
            log.info("Movie seance with id '{}' has been found.", id);
            try {
                FilmDto filmDto = filmsApiClient.getFilm(movieSeance.getFilmId());
                HallDto hallDto = cinemasApiClient.getCinemaHall(movieSeance.getCinemaId(), movieSeance.getHallId());
                InputStream poster = filmsApiClient.getFilmPoster(movieSeance.getFilmId());


                return new SeanceDto(
                        MovieSeanceMapper.INSTANCE.mapToDto(movieSeance),
                        filmDto,
                        hallDto,
                        poster
                );
            } catch (Exception e) {
                log.error("Seance api exception was occurred! " + e.getMessage());
                throw new MovieSeanceApiException("Seance api exception was occurred! " + e.getMessage());
            }
        } else {
            log.warn("Movie seance with id '{}' not found!", id);
            throw new MovieSeanceNotFoundException(String.format("Movie seance with id '%d' not found!", id));
        }
    }

    @Override
    public MovieSeanceDto createSeance(MovieSeance movieSeance) {
        HallDto hall = cinemasApiClient.getCinemaHall(movieSeance.getCinemaId(), movieSeance.getHallId());
        if (hall != null) {
            if (filmsApiClient.getFilm(movieSeance.getFilmId()) != null) {
                try {
                    List<MovieSeanceSit> sits = new ArrayList<>();
                    hall.getSits().forEach(sitDto -> {
                        MovieSeanceSit seanceSit = new MovieSeanceSit(
                                sitDto.getId(),
                                SitsStatuses.AVAILABLE
                        );
                        seanceSit = movieSeanceSitsRepository.save(seanceSit);

                        sits.add(seanceSit);
                    });

                    movieSeance.setCode(StringGenerator.generateRandomString(16));
                    movieSeance.setSits(sits);
                    movieSeance = movieSeancesRepository.save(movieSeance);
                    log.info("New movie seance with id has been created");
                    return MovieSeanceMapper.INSTANCE.mapToDto(movieSeance);
                } catch (Exception e) {
                    log.error("Movie seance creation exception! " + e.getMessage());
                    throw new MovieSeanceCreationException("Movie seance creation exception! " + e.getMessage());
                }
            } else {
                log.warn("Film with id '{}' doesn't exist!", movieSeance.getFilmId());
                throw new FilmNotFoundException(String.format("Film with id '%d' doesn't exist!", movieSeance.getFilmId()));
            }
        } else {
            log.warn("Hall with id '{}' doesn't belong to the cinema '{}' or not exist!", movieSeance.getCinemaId(), movieSeance.getHallId());
            throw new HallNotBelongCinemaException(
                    String.format("Hall with id '%d' doesn't belong to the cinema '%d' or not exist!",
                    movieSeance.getCinemaId(),
                    movieSeance.getHallId())
            );
        }
    }

    @Override
    public MovieSeanceDto updateSeance(MovieSeance movieSeance) {
        if (movieSeancesRepository.existsById(movieSeance.getId())) {
            try {
                MovieSeance updatedMovieSeance = movieSeancesRepository.save(movieSeance);
                log.info("Movie seance with id '{}' has been updated.", updatedMovieSeance.getId());
                return MovieSeanceMapper.INSTANCE.mapToDto(updatedMovieSeance);
            } catch (Exception e) {
                log.error("Can't update movie seance! " + e.getMessage());
                throw new MovieSeanceDeletingException("Can't update movie seance! " + e.getMessage());
            }
        } else {
            log.warn("Movie seance with id '{}' doesn't exist!", movieSeance.getId());
            throw new MovieSeanceNotFoundException(String.format("Movie seance with id '%d' doesn't exist!", movieSeance.getId()));
        }
    }

    @Override
    public Long deleteSeance(Long id) {
        if (movieSeancesRepository.existsById(id)) {
            try {
                movieSeancesRepository.deleteById(id);
                log.info("Movie seance with id '{}' has been deleted.", id);
                return id;
            } catch (Exception e) {
                log.error("Can't delete movie seance! " + e.getMessage());
                throw new MovieSeanceDeletingException("Can't delete movie seance! " + e.getMessage());
            }
        } else {
            log.warn("Movie seance with id '{}' doesn't exist!", id);
            throw new MovieSeanceNotFoundException(String.format("Movie seance with id '%d' doesn't exist!", id));
        }
    }
}
