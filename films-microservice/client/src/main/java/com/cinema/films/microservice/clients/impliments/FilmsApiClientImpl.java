package com.cinema.films.microservice.clients.impliments;

import com.cinema.films.microservice.clients.FilmsApiClient;
import com.cinema.films.microservice.domains.FilmDto;
import com.cinema.films.microservice.exceptions.clients.FilmsMicroserviceApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;

@Slf4j
@Component
public class FilmsApiClientImpl implements FilmsApiClient {

    @Value("${films.microservice.api.client.url}")
    private String FILMS_API_URL;

    private final static RestTemplate restTemplate = new RestTemplate();

    @Override
    public FilmDto getFilm(Long id) {
        String requestUri = FILMS_API_URL + "/films/" + id;
        try {
            ResponseEntity<FilmDto> responseEntity = restTemplate.exchange(requestUri, HttpMethod.GET, null, FilmDto.class);
            log.info("Film has bent got from 'FILMS-MICROSERVICE' by id '{}'", id);
            return responseEntity.getBody();
        } catch (RestClientException e) {
            log.error("Films rest client exception ! {}", e.getMessage());
            throw new FilmsMicroserviceApiException(String.format("Films rest client exception ! %s", e.getMessage()));
        }
    }

    @Override
    public InputStream getFilmPoster(Long filmId) {
        String requestUri = FILMS_API_URL + "/films/" + filmId + "/poster";
        try {
            ResponseEntity<InputStream> responseEntity = restTemplate.exchange(requestUri, HttpMethod.GET, null, InputStream.class);
            log.info("Film poster has been got from 'FILMS-MICROSERVICE' by id '{}'", filmId);
            return responseEntity.getBody();
        } catch (RestClientException e) {
            log.error("Films rest client exception ! {}", e.getMessage());
            throw new FilmsMicroserviceApiException(String.format("Films rest client exception ! %s", e.getMessage()));
        }
    }
}
