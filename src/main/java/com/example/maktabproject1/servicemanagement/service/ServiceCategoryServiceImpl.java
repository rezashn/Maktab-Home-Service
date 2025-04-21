package com.example.maktabproject1.servicemanagement.service;

import com.example.maktabproject1.servicemanagement.dto.ServiceCategoryDto;
import com.example.maktabproject1.servicemanagement.entity.ServiceCategoryEntity;
import com.example.maktabproject1.servicemanagement.exception.ResponseNotFoundException;
import com.example.maktabproject1.servicemanagement.repository.ServiceCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceCategoryServiceImpl implements ServiceCategoryService {

    private final ServiceCategoryRepository serviceCategoryRepository;

    @Autowired
    public ServiceCategoryServiceImpl(ServiceCategoryRepository serviceCategoryRepository) {
        this.serviceCategoryRepository = serviceCategoryRepository;
    }

    @Override
    public ServiceCategoryDto createServiceCategory(ServiceCategoryDto serviceCategoryDto) {
        ServiceCategoryEntity entity = mapDtoToEntity(serviceCategoryDto);
        ServiceCategoryEntity savedEntity = serviceCategoryRepository.save(entity);
        return mapEntityToDto(savedEntity);
    }

    @Override
    public ServiceCategoryDto getServiceCategoryById(Long id) {
        ServiceCategoryEntity entity = serviceCategoryRepository.findById(id)
                .orElseThrow(() -> new ResponseNotFoundException("Service Category not found with ID: " + id));
        return mapEntityToDto(entity);
    }

    @Override
    public List<ServiceCategoryDto> getAllServiceCategories() {
        List<ServiceCategoryEntity> entities = serviceCategoryRepository.findAll();
        return entities.stream().map(this::mapEntityToDto).collect(Collectors.toList());
    }

    @Override
    public ServiceCategoryDto updateServiceCategory(Long id, ServiceCategoryDto serviceCategoryDto) {
        ServiceCategoryEntity entity = serviceCategoryRepository.findById(id)
                .orElseThrow(() -> new ResponseNotFoundException("Service Category not found with ID: " + id));
        entity.setName(serviceCategoryDto.getName());
        entity.setDescription(serviceCategoryDto.getDescription());
        ServiceCategoryEntity updatedEntity = serviceCategoryRepository.save(entity);
        return mapEntityToDto(updatedEntity);
    }

    @Override
    public void deleteServiceCategory(Long id) {
        if (!serviceCategoryRepository.existsById(id)) {
            throw new ResponseNotFoundException("Service Category not found with ID: " + id);
        }
        serviceCategoryRepository.deleteById(id);
    }

    private ServiceCategoryDto mapEntityToDto(ServiceCategoryEntity entity) {
        ServiceCategoryDto dto = new ServiceCategoryDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    private ServiceCategoryEntity mapDtoToEntity(ServiceCategoryDto dto) {
        ServiceCategoryEntity entity = new ServiceCategoryEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        return entity;
    }
}
