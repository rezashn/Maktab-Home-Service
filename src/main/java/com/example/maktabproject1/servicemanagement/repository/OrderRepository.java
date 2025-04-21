package com.example.maktabproject1.servicemanagement.repository;

import com.example.maktabproject1.servicemanagement.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}