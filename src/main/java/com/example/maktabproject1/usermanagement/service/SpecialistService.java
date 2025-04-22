package com.example.maktabproject1.usermanagement.service;

import com.example.maktabproject1.servicemanagement.dto.SpecialistDto;
import com.example.maktabproject1.usermanagement.entity.SpecialistEntity;
import com.example.maktabproject1.usermanagement.dto.SpecialistResponseDto;

import java.math.BigDecimal;
import java.util.List;

public interface SpecialistService {

        SpecialistResponseDto getSpecialistById(Long id);

        List<SpecialistResponseDto> getAllSpecialists();

        SpecialistDto updateSpecialist(Long id, SpecialistDto dto);

        SpecialistEntity getSpecialistEntityById(Long specialistId);

        List<SpecialistResponseDto> searchSpecialistsBySubService(String subServiceName);

        List<SpecialistResponseDto> searchSpecialistsByRating(BigDecimal minRating);

        List<SpecialistResponseDto> searchSpecialistsBySubServiceAndRating(String subServiceName, BigDecimal minRating);

        List<SpecialistResponseDto> getSpecialistsBySubServiceName(String subServiceName);

        void save(SpecialistEntity specialistEntity);

}
