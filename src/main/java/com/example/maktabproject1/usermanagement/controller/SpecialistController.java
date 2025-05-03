package com.example.maktabproject1.usermanagement.controller;

import com.example.maktabproject1.ResponseDto;
import com.example.maktabproject1.servicemanagement.dto.SpecialistDto;
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
    public ResponseEntity<ResponseDto<SpecialistDto>> updateSpecialist(
            @PathVariable Long specialistId,
            @Valid @RequestBody SpecialistDto specialistDTO) {

        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        if (!specialistId.equals(userId) && !isAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseDto<>(false, null, "You can only update your own data."));
        }

        try {
            log.info("Updating specialist with ID: {}, data: {}", specialistId, specialistDTO);
            SpecialistDto updatedSpecialist = specialistService.updateSpecialist(specialistId, specialistDTO);
            return ResponseEntity.ok(new ResponseDto<>(true, updatedSpecialist, "Specialist updated successfully"));
        } catch (Exception e) {
            log.error("Update failed for specialist ID {}: {}", specialistId, e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ResponseDto<>(false, null, e.getMessage()));
        }
    }

    @GetMapping("/{specialistId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDto<SpecialistResponseDto>> getSpecialistById(@PathVariable Long specialistId) {
        try {
            log.info("Fetching specialist by ID: {}", specialistId);
            SpecialistResponseDto specialist = specialistService.getSpecialistById(specialistId);
            return ResponseEntity.ok(new ResponseDto<>(true, specialist, "Specialist found"));
        } catch (Exception e) {
            log.error("Error fetching specialist ID {}: {}", specialistId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto<>(false, null, e.getMessage()));
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDto<List<SpecialistResponseDto>>> getAllSpecialists() {
        try {
            log.info("Fetching all specialists");
            List<SpecialistResponseDto> specialists = specialistService.getAllSpecialists();
            return ResponseEntity.ok(new ResponseDto<>(true, specialists, "All specialists fetched successfully"));
        } catch (Exception e) {
            log.error("Error fetching all specialists: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto<>(false, null, "Error fetching specialists: " + e.getMessage()));
        }
    }

    @GetMapping("/by-sub-service")
    public ResponseEntity<ResponseDto<List<SpecialistResponseDto>>> getSpecialistsBySubService(@RequestParam String subServiceName) {
        try {
            log.info("Fetching specialists by sub-service name: {}", subServiceName);
            List<SpecialistResponseDto> specialists = specialistService.getSpecialistsBySubServiceName(subServiceName);
            return ResponseEntity.ok(new ResponseDto<>(true, specialists, "Specialists found by sub-service"));
        } catch (Exception e) {
            log.error("Error fetching specialists by sub-service: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto<>(false, null, "Error fetching specialists: " + e.getMessage()));
        }
    }

    @GetMapping("/search/by-sub-service")
    public ResponseEntity<ResponseDto<List<SpecialistResponseDto>>> searchSpecialistsBySubService(@RequestParam String subServiceName) {
        try {
            log.info("Searching specialists by sub-service name containing: {}", subServiceName);
            List<SpecialistResponseDto> specialists = specialistService.searchSpecialistsBySubService(subServiceName);
            return ResponseEntity.ok(new ResponseDto<>(true, specialists, "Specialists found by sub-service search"));
        } catch (Exception e) {
            log.error("Error searching specialists by sub-service: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto<>(false, null, "Error searching specialists: " + e.getMessage()));
        }
    }


    @GetMapping("/by-rating")
    public ResponseEntity<ResponseDto<List<SpecialistResponseDto>>> getSpecialistsByRating(@RequestParam BigDecimal minRating) {
        try {
            log.info("Fetching specialists with minimum rating: {}", minRating);
            List<SpecialistResponseDto> specialists = specialistService.searchSpecialistsByRating(minRating);
            return ResponseEntity.ok(new ResponseDto<>(true, specialists, "Specialists found by rating"));
        } catch (Exception e) {
            log.error("Error fetching specialists by rating: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto<>(false, null, "Error fetching specialists: " + e.getMessage()));
        }
    }

    @GetMapping("/by-sub-service-and-rating")
    public ResponseEntity<ResponseDto<List<SpecialistResponseDto>>> getSpecialistsBySubServiceAndRating(
            @RequestParam String subServiceName,
            @RequestParam BigDecimal minRating) {
        try {
            log.info("Fetching specialists by sub-service: {} and rating: {}", subServiceName, minRating);
            List<SpecialistResponseDto> specialists = specialistService.searchSpecialistsBySubServiceAndRating(subServiceName, minRating);
            return ResponseEntity.ok(new ResponseDto<>(true, specialists, "Specialists found by sub-service and rating"));
        } catch (Exception e) {
            log.error("Error fetching specialists by sub-service and rating: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto<>(false, null, "Error fetching specialists: " + e.getMessage()));
        }
    }
}
