package com.example.maktabproject1.service;

import com.example.maktabproject1.dto.SubServiceDto;
import com.example.maktabproject1.entity.SubServiceEntity;

import java.util.List;

public interface SubServiceService {

    SubServiceDto createSubService(SubServiceDto subServiceDTO);

    SubServiceDto getSubServiceById(Long id);

    List<SubServiceDto> getAllSubServices();

    SubServiceDto updateSubService(Long id, SubServiceDto subServiceDTO);

    void deleteSubService(Long id);

    SubServiceEntity getSubServiceEntityById(Long subServiceId);
}