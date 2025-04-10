package com.example.maktabproject1.service;

import com.example.maktabproject1.dto.UserCreditTransactionDto;
import java.math.BigDecimal;
import java.util.List;

public interface UserCreditTransactionService {

    UserCreditTransactionDto deposit(Long userId, BigDecimal amount, String description);

    UserCreditTransactionDto withdraw(Long userId, BigDecimal amount, String description);

    UserCreditTransactionDto createTransaction(UserCreditTransactionDto transactionDto);

    List<UserCreditTransactionDto> getTransactionsByUser(Long userId);

    BigDecimal getCurrentCredit(Long userId);

    List<UserCreditTransactionDto> getDepositHistory(Long userId);

    List<UserCreditTransactionDto> getWithdrawalHistory(Long userId);
}