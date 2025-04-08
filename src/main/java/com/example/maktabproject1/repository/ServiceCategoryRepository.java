package com.example.maktabproject1.repository;

import com.example.maktabproject1.entity.ServiceCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceCategoryRepository extends JpaRepository<ServiceCategoryEntity, Long> {

    boolean existsByName(String name);
}
