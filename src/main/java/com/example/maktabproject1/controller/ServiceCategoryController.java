package com.example.maktabproject1.controller;

import com.example.maktabproject1.dto.ServiceCategoryDto;
import com.example.maktabproject1.service.ServiceCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service-categories")
public class ServiceCategoryController {

    private final ServiceCategoryService serviceCategoryService;

    @Autowired
    public ServiceCategoryController(ServiceCategoryService serviceCategoryService) {
        this.serviceCategoryService = serviceCategoryService;
    }
    @PostMapping
    public ResponseEntity<ServiceCategoryDto> createServiceCategory(@Valid @RequestBody ServiceCategoryDto serviceCategoryDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceCategoryService.createServiceCategory(serviceCategoryDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceCategoryDto> getServiceCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceCategoryService.getServiceCategoryById(id));
    }

    @GetMapping
    public ResponseEntity<List<ServiceCategoryDto>> getAllServiceCategories() {
        return ResponseEntity.ok(serviceCategoryService.getAllServiceCategories());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceCategoryDto> updateServiceCategory(@PathVariable Long id, @Valid @RequestBody ServiceCategoryDto serviceCategoryDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(serviceCategoryService.updateServiceCategory(id, serviceCategoryDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceCategory(@PathVariable Long id) {
        serviceCategoryService.deleteServiceCategory(id);
        return ResponseEntity.noContent().build();
    }
}