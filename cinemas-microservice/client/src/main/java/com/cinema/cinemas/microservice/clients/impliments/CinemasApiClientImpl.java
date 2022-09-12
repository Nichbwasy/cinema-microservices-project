package com.cinema.cinemas.microservice.clients.impliments;

import com.cinema.cinemas.microservice.clients.CinemasApiClient;
import com.cinema.cinemas.microservice.domains.CinemaDto;
import com.cinema.cinemas.microservice.domains.HallDto;
import com.cinema.cinemas.microservice.exceptions.clients.CinemasMicroserviceApiException;
import com.cinema.common.utils.authorizations.jwt.JwtClientAuthentication;
import com.cinema.common.utils.authorizations.jwt.JwtClientTokenProvider;
import com.cinema.common.utils.authorizations.jwt.dto.ApiClientDto;
import com.cinema.common.utils.authorizations.roles.UserRoles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class CinemasApiClientImpl implements CinemasApiClient {

    @Value("${cinemas.microservice.api.client.url}")
    private String CINEMA_API_URL;

    @Value("${spring.application.name}")
    private String MICROSERVICE_NAME;

    @Autowired
    private JwtClientTokenProvider jwtClientTokenProvider;

    private final static RestTemplate restTemplate = new RestTemplate();

    @Override
    public CinemaDto getCinema(Long id) {
        String requestUri = CINEMA_API_URL + "/cinemas/" + id;
        try {
            HttpEntity<String> httpHeaders = generateTokenHeaders();
            ResponseEntity<CinemaDto> responseEntity = restTemplate.exchange(requestUri, HttpMethod.GET, httpHeaders, CinemaDto.class);
            log.info("Cinema has bent got from 'CINEMAS-MICROSERVICE' by id '{}'", id);
            return responseEntity.getBody();
        } catch (RestClientException e) {
            log.error("Cinemas rest client exception ! {}", e.getMessage());
            throw new CinemasMicroserviceApiException(String.format("Cinemas rest client exception ! %s", e.getMessage()));
        }
    }

    @Override
    public HallDto getCinemaHall(Long cinemaId, Long hallId) {
        String requestUri = CINEMA_API_URL + "/cinemas/" + cinemaId + "/halls/" + hallId;
        try {
            HttpEntity<String> httpHeaders = generateTokenHeaders();
            ResponseEntity<HallDto> responseEntity = restTemplate.exchange(requestUri, HttpMethod.GET, httpHeaders, HallDto.class);
            log.info("Hall '{}' of the cinema '{}' has bent got from 'CINEMAS-MICROSERVICE'", hallId, cinemaId);
            return responseEntity.getBody();
        } catch (RestClientException e) {
            log.error("Cinemas rest client exception ! {}", e.getMessage());
            throw new CinemasMicroserviceApiException(String.format("Cinemas rest client exception ! %s", e.getMessage()));
        }
    }

    @Override
    public List<HallDto> getCinemaHalls(Long cinemaId) {
        String requestUri = CINEMA_API_URL + "/cinemas/" + cinemaId + "/halls/";
        try {
            HttpEntity<String> httpHeaders = generateTokenHeaders();
            ResponseEntity<List<HallDto>> responseEntity = restTemplate.exchange(
                    requestUri,
                    HttpMethod.GET,
                    httpHeaders,
                    new ParameterizedTypeReference<List<HallDto>>(){});
            log.info("All halls of the cinema '{}' has bent got from 'CINEMAS-MICROSERVICE'", cinemaId);
            return responseEntity.getBody();
        } catch (RestClientException e) {
            log.error("Cinemas rest client exception ! {}", e.getMessage());
            throw new CinemasMicroserviceApiException(String.format("Cinemas rest client exception ! %s", e.getMessage()));
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
