package com.example.maktabproject1.repository;

import com.example.maktabproject1.entity.SpecialistEntity;
import com.example.maktabproject1.entity.SubServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SpecialistRepository extends JpaRepository<SpecialistEntity, Long> {

    @Query("SELECT s FROM SpecialistEntity s JOIN s.subServices ss WHERE ss.name LIKE :subServiceName")
    List<SpecialistEntity> findBySubServiceNameContaining(@Param("subServiceName") String subServiceName);

    List<SpecialistEntity> findByRatingGreaterThanEqual(BigDecimal minRating);

    @Query("SELECT s FROM SpecialistEntity s JOIN s.subServices ss " +
            "WHERE ss.name LIKE :subServiceName " +
            "AND s.rating >= :minRating")

    List<SpecialistEntity> findBySubServiceNameContainingAndRatingGreaterThanEqual(
            @Param("subServiceName") String subServiceName,
            @Param("minRating") BigDecimal minRating
    );

    List<SpecialistEntity> findBySubServices(SubServiceEntity subService);
}
