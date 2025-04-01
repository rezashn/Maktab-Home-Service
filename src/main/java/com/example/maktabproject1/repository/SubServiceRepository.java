package com.example.maktabproject1.repository;

import com.example.maktabproject1.entity.SubServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubServiceRepository extends JpaRepository<SubServiceEntity, Long> {

    boolean existsByNameAndServiceCategoryId(String name, Long serviceCategoryId);

    List<SubServiceEntity> findByServiceCategoryId(Long serviceCategoryId);

}