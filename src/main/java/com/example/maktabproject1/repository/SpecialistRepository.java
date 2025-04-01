package com.example.maktabproject1.repository;

import com.example.maktabproject1.entity.SpecialistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialistRepository extends JpaRepository<SpecialistEntity, Long> {
}
