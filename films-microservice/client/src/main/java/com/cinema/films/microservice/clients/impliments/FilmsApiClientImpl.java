package com.cinema.films.microservice.clients.impliments;

import com.cinema.films.microservice.clients.FilmsApiClient;
import com.cinema.films.microservice.domains.FilmDto;
import com.cinema.films.microservice.exceptions.clients.FilmsMicroserviceApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class FilmsApiClientImpl implements FilmsApiClient {

    private final static RestTemplate restTemplate = new RestTemplate();

    @Override
    public FilmDto getFilm(Long id) {
        String requestUri = "http://FILMS-MICROSERVICE/films/" + id;
        try {
            ResponseEntity<FilmDto> responseEntity = restTemplate.exchange(requestUri, HttpMethod.GET, null, FilmDto.class);
            log.info("Film has bent got from 'FILMS-MICROSERVICE' by id '{}'", id);
            return responseEntity.getBody();
        } catch (RestClientException e) {
            log.error("Films rest client exception ! {}", e.getMessage());
            throw new FilmsMicroserviceApiException(String.format("Films rest client exception ! %s", e.getMessage()));
        }
    }
}
