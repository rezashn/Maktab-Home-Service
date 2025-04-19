package com.example.maktabproject1.controller;

import com.example.maktabproject1.dto.ResponseDto;
import com.example.maktabproject1.dto.SpecialistDto;
import com.example.maktabproject1.entity.SubServiceEntity;
import com.example.maktabproject1.service.SpecialistService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ResponseDto<SpecialistDto>> updateSpecialist(@PathVariable Long specialistId, @Valid @RequestBody SpecialistDto specialistDTO) {
        try {
            log.info("Attempting to update specialist with ID: {}, data: {}", specialistId, specialistDTO);
            SpecialistDto updatedSpecialist = specialistService.updateSpecialist(specialistId, specialistDTO);
            log.info("Specialist updated successfully: {}", updatedSpecialist);
            ResponseDto<SpecialistDto> response = new ResponseDto<>(true, updatedSpecialist, "Specialist updated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error updating specialist: {}", e.getMessage(), e);
            ResponseDto<SpecialistDto> response = new ResponseDto<>(false, null, e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{specialistId}")
    public ResponseEntity<ResponseDto<Void>> deleteSpecialist(@PathVariable Long specialistId) {
        try {
            log.info("Attempting to delete specialist with ID: {}", specialistId);
            specialistService.deleteSpecialist(specialistId);
            log.info("Specialist deleted successfully: {}", specialistId);
            ResponseDto<Void> response = new ResponseDto<>(true, null, "Specialist deleted successfully");
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error deleting specialist: {}", e.getMessage(), e);
            ResponseDto<Void> response = new ResponseDto<>(false, null, e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/{specialistId}")
    public ResponseEntity<ResponseDto<SpecialistDto>> getSpecialistById(@PathVariable Long specialistId) {
        try {
            log.info("Fetching specialist by ID: {}", specialistId);
            SpecialistDto specialist = specialistService.getSpecialistById(specialistId);
            log.info("Specialist found: {}", specialist);
            ResponseDto<SpecialistDto> response = new ResponseDto<>(true, specialist, "Specialist found");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error fetching specialist: {}", e.getMessage(), e);
            ResponseDto<SpecialistDto> response = new ResponseDto<>(false, null, e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<SpecialistDto>>> getAllSpecialists() {
        try {
            log.info("Fetching all specialists.");
            List<SpecialistDto> specialists = specialistService.getAllSpecialists();
            log.info("Found {} specialists.", specialists.size());
            ResponseDto<List<SpecialistDto>> response = new ResponseDto<>(true, specialists, "Specialists found");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error fetching all specialists: {}", e.getMessage(), e);
            ResponseDto<List<SpecialistDto>> response = new ResponseDto<>(false, null, e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/search/sub-service")
    public ResponseEntity<ResponseDto<List<SpecialistDto>>> searchBySubService(@RequestParam String subServiceName) {
        try {
            List<SpecialistDto> specialistDtos = specialistService.searchSpecialistsBySubService(subServiceName);
            ResponseDto<List<SpecialistDto>> response = new ResponseDto<>(true, specialistDtos, "Specialists found by sub-service");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error searching by sub-service: {}", e.getMessage(), e);
            ResponseDto<List<SpecialistDto>> response = new ResponseDto<>(false, null, e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/search/rating")
    public ResponseEntity<ResponseDto<List<SpecialistDto>>> searchByRating(@RequestParam BigDecimal minRating) {
        try {
            List<SpecialistDto> specialistDtos = specialistService.searchSpecialistsByRating(minRating);
            ResponseDto<List<SpecialistDto>> response = new ResponseDto<>(true, specialistDtos, "Specialists found by rating");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error searching by rating: {}", e.getMessage(), e);
            ResponseDto<List<SpecialistDto>> response = new ResponseDto<>(false, null, e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/search/sub-service-rating")
    public ResponseEntity<ResponseDto<List<SpecialistDto>>> searchBySubServiceAndRating(
            @RequestParam String subServiceName,
            @RequestParam BigDecimal minRating) {
        try {
            List<SpecialistDto> specialistDtos = specialistService.searchSpecialistsBySubServiceAndRating(subServiceName, minRating);
            ResponseDto<List<SpecialistDto>> response = new ResponseDto<>(true, specialistDtos, "Specialists found by sub-service and rating");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error searching by sub-service and rating: {}", e.getMessage(), e);
            ResponseDto<List<SpecialistDto>> response = new ResponseDto<>(false, null, e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping("/search/sub-service-entity")
    public ResponseEntity<ResponseDto<List<SpecialistDto>>> searchBySubServiceEntity(@RequestBody SubServiceEntity subService) {
        try {
            List<SpecialistDto> specialistDtos = specialistService.searchSpecialistsBySubServiceEntity(subService);
            ResponseDto<List<SpecialistDto>> response = new ResponseDto<>(true, specialistDtos, "Specialists found by sub-service entity");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error searching by sub-service entity: {}", e.getMessage(), e);
            ResponseDto<List<SpecialistDto>> response = new ResponseDto<>(false, null, e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}

