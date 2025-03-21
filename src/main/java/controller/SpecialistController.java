package controller;

import entity.Specialist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.SpecialistService;

import java.util.List;

@RestController
@RequestMapping("/specialists")
public class SpecialistController {


    private final SpecialistService specialistService;

    @Autowired
    public SpecialistController(SpecialistService specialistService) {
        this.specialistService = specialistService;
    }

    @PostMapping
    public ResponseEntity<Specialist> addSpecialist(@RequestBody Specialist specialist) {
        Specialist addedSpecialist = specialistService.addSpecialist(specialist);
        return new ResponseEntity<>(addedSpecialist, HttpStatus.CREATED);
    }

    @PutMapping("/{specialistId}")
    public ResponseEntity<Specialist> updateSpecialist(@PathVariable Long specialistId, @RequestBody Specialist specialistDetails) {
        Specialist updatedSpecialist = specialistService.updateSpecialist(specialistId, specialistDetails);
        return ResponseEntity.ok(updatedSpecialist);
    }

    @DeleteMapping("/{specialistId}")
    public ResponseEntity<Void> deleteSpecialist(@PathVariable Long specialistId) {
        specialistService.deleteSpecialist(specialistId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{specialistId}")
    public ResponseEntity<Specialist> getSpecialistById(@PathVariable Long specialistId) {
        Specialist specialist = specialistService.getSpecialistById(specialistId);
        return ResponseEntity.ok(specialist);
    }

    @GetMapping
    public ResponseEntity<List<Specialist>> getAllSpecialists() {
        List<Specialist> specialists = specialistService.getAllSpecialists();
        return ResponseEntity.ok(specialists);
    }

    @PostMapping("/{specialistId}/subservices/{subServiceId}")
    public ResponseEntity<Void> addSubServiceToSpecialist(@PathVariable Long specialistId, @PathVariable Long subServiceId) {
        specialistService.addSubServiceToSpecialist(specialistId, subServiceId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{specialistId}/subservices/{subServiceId}")
    public ResponseEntity<Void> removeSubServiceFromSpecialist(@PathVariable Long specialistId, @PathVariable Long subServiceId) {
        specialistService.removeSubServiceFromSpecialist(specialistId, subServiceId);
        return ResponseEntity.noContent().build();
    }
}
