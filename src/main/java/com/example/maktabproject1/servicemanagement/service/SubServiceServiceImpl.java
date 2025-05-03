package com.example.maktabproject1.servicemanagement.service;

import com.example.maktabproject1.servicemanagement.dto.SubServiceDto;
import com.example.maktabproject1.servicemanagement.entity.ServiceCategoryEntity;
import com.example.maktabproject1.servicemanagement.entity.SubServiceEntity;
import com.example.maktabproject1.common.exception.ResponseNotFoundException;
import com.example.maktabproject1.servicemanagement.repository.ServiceCategoryRepository;
import com.example.maktabproject1.servicemanagement.repository.SubServiceRepository;
import com.example.maktabproject1.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubServiceServiceImpl implements SubServiceService {

    private final SubServiceRepository subServiceRepository;
    private final ServiceCategoryRepository serviceCategoryRepository;

    @Autowired
    public SubServiceServiceImpl(SubServiceRepository subServiceRepository, ServiceCategoryRepository serviceCategoryRepository) {
        this.subServiceRepository = subServiceRepository;
        this.serviceCategoryRepository = serviceCategoryRepository;
    }

    @Override
    public ResponseDto<SubServiceDto> createSubService(SubServiceDto dto) {
        SubServiceEntity entity = mapDtoToEntity(dto);
        SubServiceEntity savedEntity = subServiceRepository.save(entity);
        SubServiceDto subServiceDto = mapEntityToDto(savedEntity);
        return new ResponseDto<>(true, subServiceDto, "SubService created successfully");
    }

    @Override
    public ResponseDto<SubServiceDto> getSubServiceById(Long id) {
        SubServiceEntity entity = subServiceRepository.findById(id)
                .orElseThrow(() -> new ResponseNotFoundException("SubService not found: " + id));
        SubServiceDto subServiceDto = mapEntityToDto(entity);
        return new ResponseDto<>(true, subServiceDto, "SubService fetched successfully");
    }

    @Override
    public ResponseDto<List<SubServiceDto>> getAllSubServices() {
        List<SubServiceDto> subServiceDtos = subServiceRepository.findAll().stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
        return new ResponseDto<>(true, subServiceDtos, "All sub-services fetched successfully");
    }

    @Override
    public ResponseDto<SubServiceDto> updateSubService(Long id, SubServiceDto dto) {
        SubServiceEntity entity = subServiceRepository.findById(id)
                .orElseThrow(() -> new ResponseNotFoundException("SubService not found: " + id));
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        ServiceCategoryEntity serviceCategory = serviceCategoryRepository.findById(dto.getServiceCategoryId())
                .orElseThrow(() -> new ResponseNotFoundException("Service category not found: " + dto.getServiceCategoryId()));
        entity.setServiceCategory(serviceCategory);
        entity.setBasePrice(dto.getBasePrice());
        SubServiceEntity updatedEntity = subServiceRepository.save(entity);
        SubServiceDto updatedSubServiceDto = mapEntityToDto(updatedEntity);
        return new ResponseDto<>(true, updatedSubServiceDto, "SubService updated successfully");
    }

    @Override
    public ResponseDto<Void> deleteSubService(Long id) {
        if (!subServiceRepository.existsById(id)) {
            throw new ResponseNotFoundException("SubService not found: " + id);
        }
        subServiceRepository.deleteById(id);
        return new ResponseDto<>(true, null, "SubService deleted successfully");
    }

    private SubServiceEntity mapDtoToEntity(SubServiceDto dto) {
        SubServiceEntity entity = new SubServiceEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        ServiceCategoryEntity serviceCategory = serviceCategoryRepository.findById(dto.getServiceCategoryId())
                .orElseThrow(() -> new ResponseNotFoundException("Service category not found: " + dto.getServiceCategoryId()));
        entity.setServiceCategory(serviceCategory);
        entity.setBasePrice(dto.getBasePrice());
        return entity;
    }

    private SubServiceDto mapEntityToDto(SubServiceEntity entity) {
        SubServiceDto dto = new SubServiceDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setServiceCategoryId(entity.getServiceCategory().getId());
        dto.setBasePrice(entity.getBasePrice());
        return dto;
    }
}
