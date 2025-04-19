package com.example.maktabproject1.repository;

import com.example.maktabproject1.entity.UserCreditTransactionEntity;
import com.example.maktabproject1.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCreditTransactionRepository extends JpaRepository<UserCreditTransactionEntity, Long> {
    List<UserCreditTransactionEntity> findByUser(UserEntity user);
}
