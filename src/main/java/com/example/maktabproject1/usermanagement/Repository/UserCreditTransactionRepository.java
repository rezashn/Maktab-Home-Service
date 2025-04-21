package com.example.maktabproject1.usermanagement.Repository;

import com.example.maktabproject1.usermanagement.entity.UserCreditTransactionEntity;
import com.example.maktabproject1.usermanagement.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCreditTransactionRepository extends JpaRepository<UserCreditTransactionEntity, Long> {
    List<UserCreditTransactionEntity> findByUser(UserEntity user);
}
