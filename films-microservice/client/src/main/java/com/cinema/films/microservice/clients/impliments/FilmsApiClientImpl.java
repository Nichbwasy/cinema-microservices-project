package com.cinema.films.microservice.clients.impliments;

import com.cinema.common.utils.authorizations.jwt.JwtClientTokenProvider;
import com.cinema.common.utils.authorizations.jwt.dto.ApiClientDto;
import com.cinema.common.utils.authorizations.roles.UserRoles;
import com.cinema.films.microservice.clients.FilmsApiClient;
import com.cinema.films.microservice.domains.FilmDto;
import com.cinema.films.microservice.exceptions.clients.FilmsMicroserviceApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.util.Collections;

@Slf4j
@Component
public class FilmsApiClientImpl implements FilmsApiClient {

    @Value("${films.microservice.api.client.url}")
    private String FILMS_API_URL;

    @Value("${spring.application.name}")
    private String MICROSERVICE_NAME;

    @Autowired
    private JwtClientTokenProvider jwtClientTokenProvider;

    private final static RestTemplate restTemplate = new RestTemplate();

    @Override
    public FilmDto getFilm(Long id) {
        String requestUri = FILMS_API_URL + "/films/" + id;
        try {
            HttpEntity<String> httpHeaders = generateTokenHeaders();
            ResponseEntity<FilmDto> responseEntity = restTemplate.exchange(requestUri, HttpMethod.GET, httpHeaders, FilmDto.class);
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
            HttpEntity<String> httpHeaders = generateTokenHeaders();
            ResponseEntity<InputStream> responseEntity = restTemplate.exchange(requestUri, HttpMethod.GET, httpHeaders, InputStream.class);
            log.info("Film poster has been got from 'FILMS-MICROSERVICE' by id '{}'", filmId);
            return responseEntity.getBody();
        } catch (RestClientException e) {
            log.error("Films rest client exception ! {}", e.getMessage());
            throw new FilmsMicroserviceApiException(String.format("Films rest client exception ! %s", e.getMessage()));
        }
    }

    private HttpEntity<String> generateTokenHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        ApiClientDto clientDto = new ApiClientDto(MICROSERVICE_NAME, Collections.singletonList(UserRoles.API_MICROSERVICE));
        String token = jwtClientTokenProvider.generateClientAccessJwtToken(clientDto);
        httpHeaders.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set("Authorization", "Bearer " + token);
        return new HttpEntity<>(httpHeaders);
    }
}
