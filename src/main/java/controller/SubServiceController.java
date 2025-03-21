package controller;

import entity.SubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.SubServiceService;

import java.util.List;

@RestController
@RequestMapping("/subservices")
public class SubServiceController {

    private final SubServiceService subServiceService;

    @Autowired
    public SubServiceController(SubServiceService subServiceService) {
        this.subServiceService = subServiceService;
    }

    @PostMapping
    public ResponseEntity<SubService> addSubService(@RequestBody SubService subService) {
        SubService addedSubService = subServiceService.addSubService(subService);
        return new ResponseEntity<>(addedSubService, HttpStatus.CREATED);
    }

    @PutMapping("/{subServiceId}")
    public ResponseEntity<SubService> updateSubService(@PathVariable Long subServiceId, @RequestBody SubService subServiceDetails) {
        SubService updatedSubService = subServiceService.updateSubService(subServiceId, subServiceDetails);
        return ResponseEntity.ok(updatedSubService);
    }

    @DeleteMapping("/{subServiceId}")
    public ResponseEntity<Void> deleteSubService(@PathVariable Long subServiceId) {
        subServiceService.deleteSubService(subServiceId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{subServiceId}")
    public ResponseEntity<SubService> getSubServiceById(@PathVariable Long subServiceId) {
        SubService subService = subServiceService.getSubServiceById(subServiceId);
        return ResponseEntity.ok(subService);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<SubService>> getSubServicesByCategoryId(@PathVariable Long categoryId) {
        List<SubService> subServices = subServiceService.getSubServicesByCategoryId(categoryId);
        return ResponseEntity.ok(subServices);
    }
}
