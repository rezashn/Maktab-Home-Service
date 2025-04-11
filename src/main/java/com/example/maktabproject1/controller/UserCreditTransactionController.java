package com.example.maktabproject1.controller;

import com.example.maktabproject1.dto.UserCreditTransactionDto;
import com.example.maktabproject1.service.UserCreditTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/user-credit-transactions")
public class UserCreditTransactionController {

    private final UserCreditTransactionService transactionService;

    @Autowired
    public UserCreditTransactionController(UserCreditTransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/deposit/{userId}")
    public ResponseEntity<UserCreditTransactionDto> deposit(
            @PathVariable Long userId,
            @RequestParam BigDecimal amount,
            @RequestParam String description) {
        UserCreditTransactionDto transaction = transactionService.deposit(userId, amount, description);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @PostMapping("/withdraw/{userId}")
    public ResponseEntity<UserCreditTransactionDto> withdraw(
            @PathVariable Long userId,
            @RequestParam BigDecimal amount,
            @RequestParam String description) {
        UserCreditTransactionDto transaction = transactionService.withdraw(userId, amount, description);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserCreditTransactionDto>> getTransactionsByUser(@PathVariable Long userId) {
        List<UserCreditTransactionDto> transactions = transactionService.getTransactionsByUser(userId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/current-credit")
    public ResponseEntity<BigDecimal> getCurrentCredit(@PathVariable Long userId) {
        BigDecimal currentCredit = transactionService.getCurrentCredit(userId);
        return new ResponseEntity<>(currentCredit, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/deposits")
    public ResponseEntity<List<UserCreditTransactionDto>> getDepositHistory(@PathVariable Long userId) {
        List<UserCreditTransactionDto> deposits = transactionService.getDepositHistory(userId);
        return new ResponseEntity<>(deposits, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/withdrawals")
    public ResponseEntity<List<UserCreditTransactionDto>> getWithdrawalHistory(@PathVariable Long userId) {
        List<UserCreditTransactionDto> withdrawals = transactionService.getWithdrawalHistory(userId);
        return new ResponseEntity<>(withdrawals, HttpStatus.OK);
    }
}