package com.cinema.afisha.microservice.services;

import com.cinema.afisha.microservice.domains.MovieSeanceDto;
import com.cinema.afisha.microservice.exceptions.services.seances.MovieSeanceCreationException;
import com.cinema.afisha.microservice.exceptions.services.seances.MovieSeanceDeletingException;
import com.cinema.afisha.microservice.exceptions.services.seances.MovieSeanceNotFoundException;
import com.cinema.afisha.microservice.exceptions.services.seances.api.MovieSeanceApiException;
import com.cinema.afisha.microservice.models.MovieSeance;
import com.cinema.afisha.microservice.services.dto.SeanceDto;
import com.cinema.cinemas.microservice.exceptions.services.halls.HallNotBelongCinemaException;
import com.cinema.films.microservice.exceptions.services.films.FilmNotFoundException;

import java.util.List;

public interface MovieSeancesService {
    /**
     * Returns page existed seances from repository.
     * @param page Page of seances to return.
     * @return List of existed seances.
     *         If records for this page does't exist or no one seance has in repository, returns empty list.
     * @throws MovieSeanceApiException Throws when not possible to get film or hall information from other microservice.
     */
    List<SeanceDto> getPageOfSeances(Integer page) throws MovieSeanceApiException;

    /**
     * Returns existed seance from repository.
     * @param id Id of seance to return.
     * @return Seance data.
     * @throws MovieSeanceNotFoundException Throws when seance with selected id doesn't exist.
     * @throws MovieSeanceApiException Throws when not possible to get film or hall information from other microservice.
     */
    SeanceDto getSeance(Long id) throws MovieSeanceNotFoundException, MovieSeanceApiException;

    /**
     * Creates a new record about seance in repository.
     * @param movieSeance Movie seance data to save.
     * @return Returns DTO of saved seance.
     * @throws HallNotBelongCinemaException Throws when selected hall doesnt belong to the selected cinema in cinema-microservice.
     * @throws FilmNotFoundException Throws if selected film id doesn't belong any film in films-microservice.
     * @throws MovieSeanceCreationException Throws if any exception occurred while saving a new seance.
     */
    MovieSeanceDto createSeance(MovieSeance movieSeance)throws HallNotBelongCinemaException, FilmNotFoundException, MovieSeanceCreationException;

    /**
     * Removes a movies seance from repository.
     * @param id Id of movie seance to remove.
     * @return Id of removed seance.
     * @throws MovieSeanceNotFoundException Throws if movie seance with selected id not exist in repository.
     * @throws MovieSeanceDeletingException Throws if any exception occurred while deleting a seance.
     */
    Long deleteSeance(Long id) throws MovieSeanceNotFoundException, MovieSeanceDeletingException;
}
