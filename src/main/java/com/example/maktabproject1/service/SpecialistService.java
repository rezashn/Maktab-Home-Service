package com.example.maktabproject1.service;

import com.example.maktabproject1.dto.SpecialistDto;
import com.example.maktabproject1.entity.SpecialistEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SpecialistService {

        SpecialistDto createSpecialist(SpecialistDto specialistDTO);

        SpecialistDto getSpecialistById(Long id);

        List<SpecialistDto> getAllSpecialists();

        SpecialistDto updateSpecialist(Long id, SpecialistDto specialistDTO);

        void deleteSpecialist(Long id);

        SpecialistEntity getSpecialistEntityById(Long specialistId);

        void setSpecialistImage(Long specialistId, MultipartFile image) throws IOException;
}