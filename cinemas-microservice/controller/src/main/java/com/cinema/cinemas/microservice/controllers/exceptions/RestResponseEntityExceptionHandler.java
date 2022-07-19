package com.cinema.cinemas.microservice.controllers.exceptions;

import com.cinema.cinemas.microservice.exceptions.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value =
            {EntityCreationException.class,
            EntityDeletingException.class,
            EntityUpdatingException.class,
            EntityCreationException.class})
    protected ResponseEntity<Object> serviceException(Exception e, WebRequest request) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        log.error("Service exception! " + e.getMessage());

        responseBody.put("message", String.format("Service exception! %s", e.getMessage()));
        responseBody.put("time", LocalDateTime.now());
        return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {EntityAlreadyExistException.class})
    protected ResponseEntity<Object> badRequestException(Exception e, WebRequest request) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        log.error("Bad request! " + e.getMessage());

        responseBody.put("message", String.format("Bad request! %s", e.getMessage()));
        responseBody.put("time", LocalDateTime.now());
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    protected ResponseEntity<Object> notFoundException(Exception e, WebRequest request) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        log.error("Not found! " + e.getMessage());

        responseBody.put("message", String.format("Not found! %s", e.getMessage()));
        responseBody.put("time", LocalDateTime.now());
        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {RuntimeException.class, Exception.class})
    protected ResponseEntity<Object> unknownException(Exception e, WebRequest request) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        log.error("Internal server error! " + e.getMessage());

        responseBody.put("message", String.format("Internal server error! %s", e.getMessage()));
        responseBody.put("time", LocalDateTime.now());
        return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        log.error("Validation exception! {}", ex.getMessage());

        body.put("message", String.format("Validation exception! %s", ex.getMessage()));
        body.put("time", LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}
