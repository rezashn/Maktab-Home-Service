package com.example.maktabproject1.controller;

import com.example.maktabproject1.dto.ResponseDto;
import com.example.maktabproject1.dto.ServiceCategoryDto;
import com.example.maktabproject1.service.ServiceCategoryService;
import jakarta.validation.Valid;
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
    public ResponseEntity<ResponseDto<ServiceCategoryDto>> createServiceCategory(@Valid @RequestBody ServiceCategoryDto serviceCategoryDTO) {
        try {
            log.info("Attempting to create service category: {}", serviceCategoryDTO);
            ServiceCategoryDto createdCategory = serviceCategoryService.createServiceCategory(serviceCategoryDTO);
            log.info("Service category created successfully: {}", createdCategory);
            ResponseDto<ServiceCategoryDto> response = new ResponseDto<>(true, createdCategory, "Service category created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("Error creating service category: {}", e.getMessage(), e);
            ResponseDto<ServiceCategoryDto> response = new ResponseDto<>(false, null, e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<ServiceCategoryDto>> getServiceCategoryById(@PathVariable Long id) {
        try {
            log.info("Fetching service category by ID: {}", id);
            ServiceCategoryDto category = serviceCategoryService.getServiceCategoryById(id);
            log.info("Service category found: {}", category);
            ResponseDto<ServiceCategoryDto> response = new ResponseDto<>(true, category, "Service category found");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error fetching service category: {}", e.getMessage(), e);
            ResponseDto<ServiceCategoryDto> response = new ResponseDto<>(false, null, e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<ServiceCategoryDto>>> getAllServiceCategories() {
        try {
            log.info("Fetching all service categories.");
            List<ServiceCategoryDto> categories = serviceCategoryService.getAllServiceCategories();
            log.info("Found {} service categories.", categories.size());
            ResponseDto<List<ServiceCategoryDto>> response = new ResponseDto<>(true, categories, "Service categories found");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error fetching all service categories: {}", e.getMessage(), e);
            ResponseDto<List<ServiceCategoryDto>> response = new ResponseDto<>(false, null, e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<ServiceCategoryDto>> updateServiceCategory(@PathVariable Long id, @Valid @RequestBody ServiceCategoryDto serviceCategoryDTO) {
        try {
            log.info("Attempting to update service category with ID: {}, data: {}", id, serviceCategoryDTO);
            ServiceCategoryDto updatedCategory = serviceCategoryService.updateServiceCategory(id, serviceCategoryDTO);
            log.info("Service category updated successfully: {}", updatedCategory);
            ResponseDto<ServiceCategoryDto> response = new ResponseDto<>(true, updatedCategory, "Service category updated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error updating service category: {}", e.getMessage(), e);
            ResponseDto<ServiceCategoryDto> response = new ResponseDto<>(false, null, e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteServiceCategory(@PathVariable Long id) {
        try {
            log.info("Attempting to delete service category with ID: {}", id);
            serviceCategoryService.deleteServiceCategory(id);
            log.info("Service category deleted successfully: {}", id);
            ResponseDto<Void> response = new ResponseDto<>(true, null, "Service category deleted successfully");
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error deleting service category: {}", e.getMessage(), e);
            ResponseDto<Void> response = new ResponseDto<>(false, null, e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
