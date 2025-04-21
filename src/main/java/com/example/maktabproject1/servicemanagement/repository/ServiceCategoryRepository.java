package com.example.maktabproject1.servicemanagement.repository;

import com.example.maktabproject1.servicemanagement.entity.ServiceCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceCategoryRepository extends JpaRepository<ServiceCategoryEntity, Long> {

    boolean existsByName(String name);
}
