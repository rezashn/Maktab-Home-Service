package com.example.maktabproject1.usermanagement.controller;

import com.example.maktabproject1.ResponseDto;
import com.example.maktabproject1.servicemanagement.dto.SpecialistDto;
import com.example.maktabproject1.servicemanagement.entity.SubServiceEntity;
import com.example.maktabproject1.usermanagement.dto.SpecialistResponseDto;
import com.example.maktabproject1.usermanagement.service.SpecialistService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        if (!specialistId.equals(userId) && !isAdmin) {
            ResponseDto<SpecialistDto> response = new ResponseDto<>(false, null, "You can only update your own data.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

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

    @GetMapping("/{specialistId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDto<SpecialistResponseDto>> getSpecialistById(@PathVariable Long specialistId) {
        try {
            log.info("Fetching specialist by ID: {}", specialistId);
            SpecialistResponseDto specialist = specialistService.getSpecialistById(specialistId);
            log.info("Specialist found: {}", specialist);
            ResponseDto<SpecialistResponseDto> response = new ResponseDto<>(true, specialist, "Specialist found");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error fetching specialist: {}", e.getMessage(), e);
            ResponseDto<SpecialistResponseDto> response = new ResponseDto<>(false, null, e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDto<List<SpecialistResponseDto>>> getAllSpecialists() {
        try {
            List<SpecialistResponseDto> specialists = specialistService.getAllSpecialists();
            return ResponseEntity.ok(new ResponseDto<>(true, specialists, "Specialists found"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ResponseDto<>(false, null, "Error fetching specialists: " + e.getMessage()));
        }
    }


    @GetMapping("/search/sub-service")
    public ResponseEntity<ResponseDto<List<SpecialistResponseDto>>> searchBySubService(@RequestParam String subServiceName) {
        try {
            List<SpecialistResponseDto> specialistDtos = specialistService.searchSpecialistsBySubService(subServiceName);
            return ResponseEntity.ok(new ResponseDto<>(true, specialistDtos, "Specialists found by sub-service"));
        } catch (Exception e) {
            log.error("Error searching by sub-service: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body(new ResponseDto<>(false, null, "Error searching specialists: " + e.getMessage()));
        }
    }


    @GetMapping("/search/rating")
    public ResponseEntity<ResponseDto<List<SpecialistResponseDto>>> searchByRating(@RequestParam BigDecimal minRating) {
        try {
            List<SpecialistResponseDto> specialistResponses = specialistService.searchSpecialistsByRating(minRating);
            ResponseDto<List<SpecialistResponseDto>> response = new ResponseDto<>(true, specialistResponses, "Specialists found by rating");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error searching by rating: {}", e.getMessage(), e);
            ResponseDto<List<SpecialistResponseDto>> response = new ResponseDto<>(false, null, e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }


    @GetMapping("/search/sub-service-rating")
    public ResponseEntity<ResponseDto<List<SpecialistResponseDto>>> searchBySubServiceAndRating(
            @RequestParam String subServiceName,
            @RequestParam BigDecimal minRating) {
        try {
            List<SpecialistResponseDto> specialistDtos = specialistService.searchSpecialistsBySubServiceAndRating(subServiceName, minRating);
            ResponseDto<List<SpecialistResponseDto>> response = new ResponseDto<>(true, specialistDtos, "Specialists found by sub-service and rating");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error searching by sub-service and rating: {}", e.getMessage(), e);
            ResponseDto<List<SpecialistResponseDto>> response = new ResponseDto<>(false, null, e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}


