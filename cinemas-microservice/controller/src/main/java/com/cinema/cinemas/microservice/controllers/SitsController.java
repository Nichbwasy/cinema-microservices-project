package com.cinema.cinemas.microservice.controllers;

import com.cinema.cinemas.microservice.domains.SitDto;
import com.cinema.cinemas.microservice.models.Sit;
import com.cinema.cinemas.microservice.services.SitsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/sits")
public class SitsController {

    @Autowired
    private SitsService sitsService;

    @GetMapping("/{id}")
    public ResponseEntity<SitDto> getSit(@PathVariable Long id) {
        SitDto sitDto = sitsService.getSit(id);
        log.info("Sit with id '{}' has been returned.", sitDto);
        return ResponseEntity.ok(sitDto);
    }

    @PostMapping
    public ResponseEntity<Long> createSit(@ModelAttribute @Valid Sit sit) {
        SitDto sitDto = sitsService.createSit(sit);
        log.info("New sit has been created successfully: [{}].", sitDto);
        return ResponseEntity.ok(sitDto.getId());
    }

    @PutMapping
    public ResponseEntity<Long> updatedSit(@ModelAttribute @Valid Sit sit) {
        SitDto sitDto = sitsService.updateSit(sit);
        log.info("Sit has been updated successfully: [{}].", sitDto);
        return ResponseEntity.ok(sitDto.getId());
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Long> deleteSit(@PathVariable Long id) {
        Long deletedId = sitsService.deleteSit(id);
        log.info("Sit with id '{}' has been deleted successfully.", deletedId);
        return ResponseEntity.ok(deletedId);
    }
}
