package com.cinema.cinemas.microservice.controllers;

import com.cinema.cinemas.microservice.domains.SitTypeDto;
import com.cinema.cinemas.microservice.models.SitType;
import com.cinema.cinemas.microservice.services.SitsTypesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/sits/types")
public class SitsTypesController {

    @Autowired
    private SitsTypesService sitsTypesService;

    @GetMapping
    public ResponseEntity<List<SitTypeDto>> getAllSitsTypes() {
        List<SitTypeDto> sitTypeDto = sitsTypesService.getAllSitsTypes();
        log.info("All '{}' sit types has been returned.", sitTypeDto.size());
        return ResponseEntity.ok().body(sitTypeDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SitTypeDto> getSitType(@PathVariable Long id) {
        SitTypeDto sitTypeDto = sitsTypesService.getSitType(id);
        log.info("Sit type with id '{}' has been returned: [{}].", id, sitTypeDto);
        return ResponseEntity.ok().body(sitTypeDto);
    }


    @PostMapping
    public ResponseEntity<Long> createSitType(@ModelAttribute @Valid SitType sitType) {
        SitTypeDto sitTypeDto = sitsTypesService.createSitType(sitType);
        log.info("New sit type has been created successfully: [{}].", sitTypeDto);
        return ResponseEntity.ok(sitTypeDto.getId());
    }

    @PutMapping
    public ResponseEntity<Long> updateSitType(@ModelAttribute @Valid SitType sitType) {
        SitTypeDto sitTypeDto = sitsTypesService.updateSitType(sitType);
        log.info("Sit type has been updated successfully: [{}].", sitTypeDto);
        return ResponseEntity.ok(sitTypeDto.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteSitType(@PathVariable Long id) {
        Long deletedId = sitsTypesService.deleteSitType(id);
        log.info("Sit type with id '{}' has been deleted successfully.", deletedId);
        return ResponseEntity.ok(deletedId);
    }

}
