package com.example.maktabproject1.service;

import com.example.maktabproject1.entity.SubServiceEntity;
import com.example.maktabproject1.exception.DuplicateResourceException;
import com.example.maktabproject1.exception.ResponseNotFoundException;
import com.example.maktabproject1.repository.SubServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubServiceServiceImpl implements SubServiceService {


    private final SubServiceRepository subServiceRepository;
    private final ServiceCategoryServiceImpl serviceCategoryServiceImpl;

    @Autowired
    public SubServiceServiceImpl(SubServiceRepository subServiceRepository, ServiceCategoryServiceImpl serviceCategoryServiceImpl) {
        this.subServiceRepository = subServiceRepository;
        this.serviceCategoryServiceImpl = serviceCategoryServiceImpl;
    }

    @Override
    public SubServiceEntity addSubService(SubServiceEntity subServiceEntity) {
        serviceCategoryServiceImpl.getServiceCategoryById(subServiceEntity.getServiceCategory().getId());
        if (subServiceRepository.existsByNameAndServiceCategoryId(subServiceEntity.getName(), subServiceEntity.getServiceCategory().getId())) {
            throw new DuplicateResourceException("Sub service name already exists in this category");
        }
        return subServiceRepository.save(subServiceEntity);
    }

    @Override
    public SubServiceEntity updateSubService(Long id, SubServiceEntity subServiceEntity) {
        SubServiceEntity existingSubService = subServiceRepository.findById(id)
                .orElseThrow(() -> new ResponseNotFoundException("SubService not found"));
        existingSubService.setName(subServiceEntity.getName());
        existingSubService.setDescription(subServiceEntity.getDescription());
        existingSubService.setBasePrice(subServiceEntity.getBasePrice());
        existingSubService.setServiceCategory(serviceCategoryServiceImpl.getServiceCategoryById(subServiceEntity.getServiceCategory().getId()));
        return subServiceRepository.save(existingSubService);
    }

    @Override
    public void deleteSubService(Long id) {
        if (!subServiceRepository.existsById(id)) {
            throw new ResponseNotFoundException("SubService not found");
        }
        subServiceRepository.deleteById(id);
    }

    @Override
    public SubServiceEntity getSubServiceById(Long id) {
        return subServiceRepository.findById(id).orElseThrow(() -> new ResponseNotFoundException("SubService Not found"));
    }

    @Override
    public List<SubServiceEntity> getAllSubServicesByServiceCategoryId(Long serviceCategoryId) {
        return subServiceRepository.findByServiceCategoryId(serviceCategoryId);
    }

    @Override
    public List<SubServiceEntity> getSubServicesByCategoryId(Long categoryId) {
        return subServiceRepository.findByServiceCategoryId(categoryId);
    }
}
