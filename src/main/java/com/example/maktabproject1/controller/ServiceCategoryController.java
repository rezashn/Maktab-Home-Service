package com.example.maktabproject1.controller;

import com.example.maktabproject1.dto.ServiceCategoryDto;
import com.example.maktabproject1.service.ServiceCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service-categories")
public class ServiceCategoryController {

    private final ServiceCategoryService serviceCategoryService;
    private static final Logger log = LoggerFactory.getLogger(ServiceCategoryController.class);

    @Autowired
    public ServiceCategoryController(ServiceCategoryService serviceCategoryService) {
        this.serviceCategoryService = serviceCategoryService;
    }

    @PostMapping
    public ResponseEntity<ServiceCategoryDto> createServiceCategory(@RequestBody ServiceCategoryDto serviceCategoryDTO) {
        try {
            log.info("Attempting to create service category: {}", serviceCategoryDTO);
            ServiceCategoryDto createdCategory = serviceCategoryService.createServiceCategory(serviceCategoryDTO);
            log.info("Service category created successfully: {}", createdCategory);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
        } catch (Exception e) {
            log.error("Error creating service category: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceCategoryDto> getServiceCategoryById(@PathVariable Long id) {
        try {
            log.info("Fetching service category by ID: {}", id);
            ServiceCategoryDto category = serviceCategoryService.getServiceCategoryById(id);
            log.info("Service category found: {}", category);
            return ResponseEntity.ok(category);
        } catch (Exception e) {
            log.error("Error fetching service category: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ServiceCategoryDto>> getAllServiceCategories() {
        try {
            log.info("Fetching all service categories.");
            List<ServiceCategoryDto> categories = serviceCategoryService.getAllServiceCategories();
            log.info("Found {} service categories.", categories.size());
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            log.error("Error fetching all service categories: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceCategoryDto> updateServiceCategory(@PathVariable Long id, @RequestBody ServiceCategoryDto serviceCategoryDTO) {
        try {
            log.info("Attempting to update service category with ID: {}, data: {}", id, serviceCategoryDTO);
            ServiceCategoryDto updatedCategory = serviceCategoryService.updateServiceCategory(id, serviceCategoryDTO);
            log.info("Service category updated successfully: {}", updatedCategory);
            return ResponseEntity.ok(updatedCategory);
        } catch (Exception e) {
            log.error("Error updating service category: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceCategory(@PathVariable Long id) {
        try {
            log.info("Attempting to delete service category with ID: {}", id);
            serviceCategoryService.deleteServiceCategory(id);
            log.info("Service category deleted successfully: {}", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error deleting service category: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
}