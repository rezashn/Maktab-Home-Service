package com.example.maktabproject1.usermanagement.Repository;

import com.example.maktabproject1.usermanagement.entity.UserCreditTransactionEntity;
import com.example.maktabproject1.usermanagement.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserCreditTransactionRepository extends JpaRepository<UserCreditTransactionEntity, Long> {

    List<UserCreditTransactionEntity> findByUser(UserEntity user);

    List<UserCreditTransactionEntity> findByUserAndTransactionDateBetween(UserEntity user, LocalDateTime start, LocalDateTime end);

    List<UserCreditTransactionEntity> findByUserAndAmountGreaterThan(UserEntity user, BigDecimal amount);

    List<UserCreditTransactionEntity> findByUserAndAmountLessThan(UserEntity user, BigDecimal amount);

    List<UserCreditTransactionEntity> findByUserAndAmountGreaterThanAndTransactionDateBetween(
            UserEntity user, BigDecimal amount, LocalDateTime start, LocalDateTime end);

    List<UserCreditTransactionEntity> findByUserAndAmountLessThanAndTransactionDateBetween(
            UserEntity user, BigDecimal amount, LocalDateTime start, LocalDateTime end);
}
