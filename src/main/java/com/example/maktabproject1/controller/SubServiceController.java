package com.example.maktabproject1.controller;

import com.example.maktabproject1.dto.SubServiceDto;
import com.example.maktabproject1.service.SubServiceServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sub-services")
public class SubServiceController {

    private final SubServiceServiceImpl subServiceService;
    private static final Logger log = LoggerFactory.getLogger(SubServiceController.class);

    @Autowired
    public SubServiceController(SubServiceServiceImpl subServiceService) {
        this.subServiceService = subServiceService;
    }

    @PostMapping
    public ResponseEntity<SubServiceDto> createSubService(@RequestBody SubServiceDto dto) {
        try {
            log.info("Attempting to create a sub-service: {}", dto);
            SubServiceDto createdSubService = subServiceService.createSubService(dto);
            log.info("Sub-service created successfully: {}", createdSubService);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSubService);
        } catch (Exception e) {
            log.error("Error creating sub-service: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubServiceDto> getSubServiceById(@PathVariable Long id) {
        log.info("Fetching sub-service by ID: {}", id);
        try {
            SubServiceDto subServiceDto = subServiceService.getSubServiceById(id);
            log.info("Sub-service found: {}", subServiceDto);
            return ResponseEntity.ok(subServiceDto);
        } catch (Exception e) {
            log.error("Error fetching sub-service: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<SubServiceDto>> getAllSubServices() {
        log.info("Fetching all sub-services.");
        try {
            List<SubServiceDto> subServiceDtos = subServiceService.getAllSubServices();
            log.info("Found {} sub-services.", subServiceDtos.size());
            return ResponseEntity.ok(subServiceDtos);
        } catch (Exception e) {
            log.error("Error fetching all sub-services: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubServiceDto> updateSubService(@PathVariable Long id, @RequestBody SubServiceDto dto) {
        log.info("Attempting to update sub-service with ID: {}, data: {}", id, dto);
        try {
            SubServiceDto updatedSubService = subServiceService.updateSubService(id, dto);
            log.info("Sub-service updated successfully: {}", updatedSubService);
            return ResponseEntity.ok(updatedSubService);
        } catch (Exception e) {
            log.error("Error updating sub-service: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubService(@PathVariable Long id) {
        log.info("Attempting to delete sub-service with ID: {}", id);
        try {
            subServiceService.deleteSubService(id);
            log.info("Sub-service deleted successfully: {}", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error deleting sub-service: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
}