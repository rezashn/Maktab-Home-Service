package com.example.maktabproject1.service;

import com.example.maktabproject1.entity.ServiceCategoryEntity;
import com.example.maktabproject1.exception.DuplicateResourceException;
import com.example.maktabproject1.exception.ResponseNotFoundException;
import com.example.maktabproject1.repository.ServiceCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceCategoryServiceImpl implements ServiceCategoryService {


    private final ServiceCategoryRepository serviceCategoryRepository;

    @Autowired
    public ServiceCategoryServiceImpl(ServiceCategoryRepository serviceCategoryRepository) {
        this.serviceCategoryRepository = serviceCategoryRepository;
    }

    @Override
    public ServiceCategoryEntity addServiceCategory(ServiceCategoryEntity serviceCategoryEntity) {
        if (serviceCategoryRepository.existsByName(serviceCategoryEntity.getName())) {
            throw new DuplicateResourceException("Service category name already exists");
        }
        return serviceCategoryRepository.save(serviceCategoryEntity);
    }

    @Override
    public ServiceCategoryEntity updateServiceCategory(Long id, ServiceCategoryEntity serviceCategoryEntity) {
        ServiceCategoryEntity existingCategory = serviceCategoryRepository.findById(id)
                .orElseThrow(() -> new ResponseNotFoundException("Service category not found"));
        existingCategory.setName(serviceCategoryEntity.getName());
        existingCategory.setDescription(serviceCategoryEntity.getDescription());
        return serviceCategoryRepository.save(existingCategory);
    }

    @Override
    public void deleteServiceCategory(Long id) {
        if (!serviceCategoryRepository.existsById(id)) {
            throw new ResponseNotFoundException("Service category not found");
        }
        serviceCategoryRepository.deleteById(id);
    }

    @Override
    public ServiceCategoryEntity getServiceCategoryById(Long id) {
        return serviceCategoryRepository.findById(id).orElseThrow(() -> new ResponseNotFoundException("Service Category Not found"));
    }

    @Override
    public List<ServiceCategoryEntity> getAllServiceCategories() {
        return serviceCategoryRepository.findAll();
    }
}
