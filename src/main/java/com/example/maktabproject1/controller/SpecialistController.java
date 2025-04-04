package com.example.maktabproject1.controller;

import com.example.maktabproject1.dto.SpecialistDto;
import com.example.maktabproject1.service.SpecialistService;
import com.example.maktabproject1.service.SpecialistServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/specialists")
@Slf4j
public class SpecialistController {

    private final SpecialistService specialistService;
    private final SpecialistServiceImpl specialistServiceImpl;

    @Autowired
    public SpecialistController(SpecialistService specialistService, SpecialistServiceImpl specialistServiceImpl) {
        this.specialistService = specialistService;
        this.specialistServiceImpl = specialistServiceImpl;
    }

    @PostMapping
    public ResponseEntity<SpecialistDto> addSpecialist(@Valid @RequestBody SpecialistDto specialistDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        SpecialistDto addedSpecialist = specialistService.createSpecialist(specialistDTO);
        return new ResponseEntity<>(addedSpecialist, HttpStatus.CREATED);
    }

    @PutMapping("/{specialistId}")
    public ResponseEntity<SpecialistDto> updateSpecialist(@PathVariable Long specialistId, @Valid @RequestBody SpecialistDto specialistDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        SpecialistDto updatedSpecialist = specialistService.updateSpecialist(specialistId, specialistDTO);
        return new ResponseEntity<>(updatedSpecialist, HttpStatus.OK);
    }

    @DeleteMapping("/{specialistId}")
    public ResponseEntity<Void> deleteSpecialist(@PathVariable Long specialistId) {
        specialistService.deleteSpecialist(specialistId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{specialistId}")
    public ResponseEntity<SpecialistDto> getSpecialistById(@PathVariable Long specialistId) {
        SpecialistDto specialist = specialistService.getSpecialistById(specialistId);
        return new ResponseEntity<>(specialist, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<SpecialistDto>> getAllSpecialists() {
        List<SpecialistDto> specialists = specialistService.getAllSpecialists();
        return new ResponseEntity<>(specialists, HttpStatus.OK);
    }

    @PostMapping("/{specialistId}/image")
    public ResponseEntity<String> uploadSpecialistImage(
            @PathVariable Long specialistId,
            @RequestParam("image") MultipartFile image
    ) {
        try {
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