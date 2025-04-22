package com.example.maktabproject1.servicemanagement.repository;

import com.example.maktabproject1.servicemanagement.entity.SubServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubServiceRepository extends JpaRepository<SubServiceEntity, Long> {

    Optional<SubServiceEntity> findByName(String subServiceName);

}