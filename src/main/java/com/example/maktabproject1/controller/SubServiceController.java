package com.example.maktabproject1.controller;

import com.example.maktabproject1.dto.SubServiceDto;
import com.example.maktabproject1.service.SubServiceServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sub-services")
@Slf4j
public class SubServiceController {

    private final SubServiceServiceImpl subServiceService;

    @Autowired
    public SubServiceController(SubServiceServiceImpl subServiceService) {
        this.subServiceService = subServiceService;
    }

    @PostMapping
    public ResponseEntity<SubServiceDto> createSubService(@Valid @RequestBody SubServiceDto dto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.add(error.getField() + ": " + error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(subServiceService.createSubService(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubServiceDto> getSubServiceById(@PathVariable Long id) {
        return ResponseEntity.ok(subServiceService.getSubServiceById(id));
    }

    @GetMapping
    public ResponseEntity<List<SubServiceDto>> getAllSubServices() {
        return ResponseEntity.ok(subServiceService.getAllSubServices());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubServiceDto> updateSubService(@PathVariable Long id, @Valid @RequestBody SubServiceDto dto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.add(error.getField() + ": " + error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(subServiceService.updateSubService(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubService(@PathVariable Long id) {
        subServiceService.deleteSubService(id);
        return ResponseEntity.noContent().build();
    }
}