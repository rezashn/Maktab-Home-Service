package com.example.maktabproject1.repository;

import com.example.maktabproject1.entity.OfferEntity;
import com.example.maktabproject1.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<OfferEntity, Long> {

    List<OfferEntity> findByOrderId(Long orderId);

        OfferEntity findByOrderAndIsAccepted(OrderEntity order, boolean isAccepted);

        List<OfferEntity> findByOrder(OrderEntity order);
}