package com.example.maktabproject1.usermanagement.controller;

import com.example.maktabproject1.usermanagement.dto.UserCreditTransactionDto;
import com.example.maktabproject1.ResponseDto;
import com.example.maktabproject1.usermanagement.service.UserCreditTransactionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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
    public ResponseEntity<ResponseDto<UserCreditTransactionDto>> deposit(
            @PathVariable Long userId,
            @RequestParam @Valid @Min(1) BigDecimal amount,
            @RequestParam String description) {
        ResponseDto<UserCreditTransactionDto> response = transactionService.deposit(userId, amount, description);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/withdraw/{userId}")
    public ResponseEntity<ResponseDto<UserCreditTransactionDto>> withdraw(
            @PathVariable Long userId,
            @RequestParam @Valid @Min(1) BigDecimal amount,
            @RequestParam String description) {
        ResponseDto<UserCreditTransactionDto> response = transactionService.withdraw(userId, amount, description);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
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
