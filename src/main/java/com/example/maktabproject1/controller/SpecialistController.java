package com.example.maktabproject1.controller;

import com.example.maktabproject1.dto.SpecialistDto;
import com.example.maktabproject1.entity.SubServiceEntity;
import com.example.maktabproject1.service.SpecialistService;
import com.example.maktabproject1.service.SpecialistServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/specialists")
public class SpecialistController {

    private final SpecialistService specialistService;
    private static final Logger log = LoggerFactory.getLogger(SpecialistController.class);

    @Autowired
    public SpecialistController(SpecialistService specialistService) {
        this.specialistService = specialistService;
    }

    @PutMapping("/{specialistId}")
    public ResponseEntity<SpecialistDto> updateSpecialist(@PathVariable Long specialistId, @Valid @RequestBody SpecialistDto specialistDTO) {
        try {
            log.info("Attempting to update specialist with ID: {}, data: {}", specialistId, specialistDTO);
            SpecialistDto updatedSpecialist = specialistService.updateSpecialist(specialistId, specialistDTO);
            log.info("Specialist updated successfully: {}", updatedSpecialist);
            return ResponseEntity.ok(updatedSpecialist);
        } catch (Exception e) {
            log.error("Error updating specialist: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{specialistId}")
    public ResponseEntity<Void> deleteSpecialist(@PathVariable Long specialistId) {
        try {
            log.info("Attempting to delete specialist with ID: {}", specialistId);
            specialistService.deleteSpecialist(specialistId);
            log.info("Specialist deleted successfully: {}", specialistId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error deleting specialist: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{specialistId}")
    public ResponseEntity<SpecialistDto> getSpecialistById(@PathVariable Long specialistId) {
        try {
            log.info("Fetching specialist by ID: {}", specialistId);
            SpecialistDto specialist = specialistService.getSpecialistById(specialistId);
            log.info("Specialist found: {}", specialist);
            return ResponseEntity.ok(specialist);
        } catch (Exception e) {
            log.error("Error fetching specialist: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<SpecialistDto>> getAllSpecialists() {
        try {
            log.info("Fetching all specialists.");
            List<SpecialistDto> specialists = specialistService.getAllSpecialists();
            log.info("Found {} specialists.", specialists.size());
            return ResponseEntity.ok(specialists);
        } catch (Exception e) {
            log.error("Error fetching all specialists: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/search/sub-service")
    public ResponseEntity<List<SpecialistDto>> searchBySubService(@RequestParam String subServiceName) {
        List<SpecialistDto> specialistDtos = specialistService.searchSpecialistsBySubService(subServiceName);
        return new ResponseEntity<>(specialistDtos, HttpStatus.OK);
    }

    @GetMapping("/search/rating")
    public ResponseEntity<List<SpecialistDto>> searchByRating(@RequestParam BigDecimal minRating) {
        List<SpecialistDto> specialistDtos = specialistService.searchSpecialistsByRating(minRating);
        return new ResponseEntity<>(specialistDtos, HttpStatus.OK);
    }

    @GetMapping("/search/sub-service-rating")
    public ResponseEntity<List<SpecialistDto>> searchBySubServiceAndRating(
            @RequestParam String subServiceName,
            @RequestParam BigDecimal minRating) {
        List<SpecialistDto> specialistDtos = specialistService.searchSpecialistsBySubServiceAndRating(subServiceName, minRating);
        return new ResponseEntity<>(specialistDtos, HttpStatus.OK);
    }

    @PostMapping("/search/sub-service-entity")
    public ResponseEntity<List<SpecialistDto>> searchBySubServiceEntity(@RequestBody SubServiceEntity subService) {
        List<SpecialistDto> specialistDtos = specialistService.searchSpecialistsBySubServiceEntity(subService);
        return new ResponseEntity<>(specialistDtos, HttpStatus.OK);
    }
}