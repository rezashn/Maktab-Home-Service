package com.example.maktabproject1.usermanagement.service;

import com.example.maktabproject1.usermanagement.dto.UserCreditTransactionDto;
import com.example.maktabproject1.ResponseDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface UserCreditTransactionService {
    ResponseDto<UserCreditTransactionDto> deposit(Long userId, BigDecimal amount, String description);
    ResponseDto<UserCreditTransactionDto> withdraw(Long userId, BigDecimal amount, String description);
    UserCreditTransactionDto createTransaction(UserCreditTransactionDto transactionDto);
    List<UserCreditTransactionDto> getTransactionsByUser(Long userId);
    BigDecimal getCurrentCredit(Long userId);
    List<UserCreditTransactionDto> getDepositHistory(Long userId);
    List<UserCreditTransactionDto> getWithdrawalHistory(Long userId);
    List<UserCreditTransactionDto> getTransactionsBetween(Long userId, LocalDateTime start, LocalDateTime end);
    List<UserCreditTransactionDto> getDepositsBetween(Long userId, LocalDateTime start, LocalDateTime end);
    List<UserCreditTransactionDto> getWithdrawalsBetween(Long userId, LocalDateTime start, LocalDateTime end);
}
