package com.example.maktabproject1.servicemanagement.repository;

import com.example.maktabproject1.servicemanagement.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
}