package com.example.maktabproject1.controller;

import com.example.maktabproject1.dto.ResponseDto;
import com.example.maktabproject1.dto.SubServiceDto;
import com.example.maktabproject1.service.SubServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sub-services")
public class SubServiceController {

    private final SubServiceService subServiceService;

    @Autowired
    public SubServiceController(SubServiceService subServiceService) {
        this.subServiceService = subServiceService;
    }

    @PostMapping
    public ResponseEntity<ResponseDto<SubServiceDto>> createSubService(@RequestBody SubServiceDto dto) {
        ResponseDto<SubServiceDto> response = subServiceService.createSubService(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<SubServiceDto>> getSubServiceById(@PathVariable Long id) {
        ResponseDto<SubServiceDto> response = subServiceService.getSubServiceById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<SubServiceDto>>> getAllSubServices() {
        ResponseDto<List<SubServiceDto>> response = subServiceService.getAllSubServices();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<SubServiceDto>> updateSubService(@PathVariable Long id, @RequestBody SubServiceDto dto) {
        ResponseDto<SubServiceDto> response = subServiceService.updateSubService(id, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteSubService(@PathVariable Long id) {
        ResponseDto<Void> response = subServiceService.deleteSubService(id);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
