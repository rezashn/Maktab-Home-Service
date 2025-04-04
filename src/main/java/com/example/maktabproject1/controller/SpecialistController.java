package com.example.maktabproject1.controller;

import com.example.maktabproject1.dto.SpecialistDto;
import com.example.maktabproject1.service.SpecialistService;
import com.example.maktabproject1.service.SpecialistServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/specialists")
public class SpecialistController {

    private final SpecialistService specialistService;
    private final SpecialistServiceImpl specialistServiceImpl;
    private static final Logger log = LoggerFactory.getLogger(SpecialistController.class);

    @Autowired
    public SpecialistController(SpecialistService specialistService, SpecialistServiceImpl specialistServiceImpl) {
        this.specialistService = specialistService;
        this.specialistServiceImpl = specialistServiceImpl;
    }

    @PostMapping
    public ResponseEntity<SpecialistDto> addSpecialist(@RequestBody SpecialistDto specialistDTO) {
        try {
            log.info("Attempting to add specialist: {}", specialistDTO);
            SpecialistDto addedSpecialist = specialistService.createSpecialist(specialistDTO);
            log.info("Specialist added successfully: {}", addedSpecialist);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedSpecialist);
        } catch (Exception e) {
            log.error("Error adding specialist: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{specialistId}")
    public ResponseEntity<SpecialistDto> updateSpecialist(@PathVariable Long specialistId, @RequestBody SpecialistDto specialistDTO) {
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

    @PostMapping("/{specialistId}/image")
    public ResponseEntity<String> uploadSpecialistImage(
            @PathVariable Long specialistId,
            @RequestParam("image") MultipartFile image
    ) {
        try {
            log.info("Attempting to upload image for specialist ID: {}", specialistId);
            specialistServiceImpl.setSpecialistImage(specialistId, image);
            log.info("Image uploaded successfully for specialist ID: {}", specialistId);
            return ResponseEntity.ok("Image uploaded successfully.");
        } catch (IOException e) {
            log.error("Error uploading image for specialist ID: {}", specialistId, e);
            return ResponseEntity.badRequest().body("Image upload failed: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error uploading image for specialist ID: {}", specialistId, e);
            return ResponseEntity.internalServerError().body("Unexpected error occurred.");
        }
    }
}