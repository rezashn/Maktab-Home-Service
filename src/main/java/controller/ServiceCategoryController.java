package controller;

import entity.ServiceCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ServiceCategoryService;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class ServiceCategoryController {


    private final ServiceCategoryService serviceCategoryService;

    @Autowired
    public ServiceCategoryController(ServiceCategoryService serviceCategoryService) {
        this.serviceCategoryService = serviceCategoryService;
    }

    @PostMapping
    public ResponseEntity<ServiceCategory> addServiceCategory(@RequestBody ServiceCategory serviceCategory) {
        ServiceCategory addedCategory = serviceCategoryService.addServiceCategory(serviceCategory);
        return new ResponseEntity<>(addedCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<ServiceCategory> updateServiceCategory(@PathVariable Long categoryId, @RequestBody ServiceCategory serviceCategoryDetails) {
        ServiceCategory updatedCategory = serviceCategoryService.updateServiceCategory(categoryId, serviceCategoryDetails);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteServiceCategory(@PathVariable Long categoryId) {
        serviceCategoryService.deleteServiceCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ServiceCategory> getServiceCategoryById(@PathVariable Long categoryId) {
        ServiceCategory category = serviceCategoryService.getServiceCategoryById(categoryId);
        return ResponseEntity.ok(category);
    }

    @GetMapping
    public ResponseEntity<List<ServiceCategory>> getAllServiceCategories() {
        List<ServiceCategory> categories = serviceCategoryService.getAllServiceCategories();
        return ResponseEntity.ok(categories);
    }
}
