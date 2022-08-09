package com.cinema.cinemas.microservice.clients.impliments;

import com.cinema.cinemas.microservice.clients.CinemasApiClient;
import com.cinema.cinemas.microservice.domains.CinemaDto;
import com.cinema.cinemas.microservice.domains.HallDto;
import com.cinema.cinemas.microservice.exceptions.clients.CinemasMicroserviceApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Component
public class CinemasApiClientImpl implements CinemasApiClient {

    @Value("${cinemas.microservice.api.client.url}")
    private String CINEMA_API_URL;

    private final static RestTemplate restTemplate = new RestTemplate();

    @Override
    public CinemaDto getCinema(Long id) {
        String requestUri = CINEMA_API_URL + "/cinemas/" + id;
        try {
            ResponseEntity<CinemaDto> responseEntity = restTemplate.exchange(requestUri, HttpMethod.GET, null, CinemaDto.class);
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
            ResponseEntity<HallDto> responseEntity = restTemplate.exchange(requestUri, HttpMethod.GET, null, HallDto.class);
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
            ResponseEntity<List<HallDto>> responseEntity = restTemplate.exchange(
                    requestUri,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<HallDto>>(){});
            log.info("All halls of the cinema '{}' has bent got from 'CINEMAS-MICROSERVICE'", cinemaId);
            return responseEntity.getBody();
        } catch (RestClientException e) {
            log.error("Cinemas rest client exception ! {}", e.getMessage());
            throw new CinemasMicroserviceApiException(String.format("Cinemas rest client exception ! %s", e.getMessage()));
        }
    }
}
