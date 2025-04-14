package com.example.maktabproject1.service;

import com.example.maktabproject1.dto.SpecialistDto;
import com.example.maktabproject1.entity.SpecialistEntity;
import com.example.maktabproject1.entity.SubServiceEntity;
import com.example.maktabproject1.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface SpecialistService {

        SpecialistEntity createSpecialistFromUser(UserEntity user);

        SpecialistDto getSpecialistById(Long id);

        List<SpecialistDto> getAllSpecialists();

        SpecialistDto updateSpecialist(Long id, SpecialistDto specialistDTO);

        void deleteSpecialist(Long id);

        SpecialistEntity getSpecialistEntityById(Long specialistId);

        List<SpecialistDto> searchSpecialistsBySubService(String subServiceName);

        List<SpecialistDto> searchSpecialistsByRating(BigDecimal minRating);

        List<SpecialistDto> searchSpecialistsBySubServiceAndRating(String subServiceName, BigDecimal minRating);

        List<SpecialistDto> searchSpecialistsBySubServiceEntity(SubServiceEntity subService);

}