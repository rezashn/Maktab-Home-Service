package com.example.maktabproject1.service;

import com.example.maktabproject1.entity.SubServiceEntity;

import java.util.List;

public interface SubServiceService {
    SubServiceEntity addSubService(SubServiceEntity subServiceEntity);

    SubServiceEntity updateSubService(Long subServiceId, SubServiceEntity subServiceEntity);

    void deleteSubService(Long subServiceId);

    SubServiceEntity getSubServiceById(Long subServiceId);

    List<SubServiceEntity> getSubServicesByCategoryId(Long categoryId);

    public List<SubServiceEntity> getAllSubServicesByServiceCategoryId(Long serviceCategoryId);
}
