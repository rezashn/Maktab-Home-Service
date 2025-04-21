package com.example.maktabproject1.usermanagement.service;

import com.example.maktabproject1.usermanagement.dto.UserCreditTransactionDto;
import com.example.maktabproject1.ResponseDto;
import com.example.maktabproject1.usermanagement.entity.UserCreditTransactionEntity;
import com.example.maktabproject1.usermanagement.entity.UserEntity;
import com.example.maktabproject1.usermanagement.Repository.UserCreditTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserCreditTransactionServiceImpl implements UserCreditTransactionService {

    private final UserCreditTransactionRepository transactionRepository;
    private final UserService userService;

    @Autowired
    public UserCreditTransactionServiceImpl(UserCreditTransactionRepository transactionRepository, UserService userService) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
    }

    @Override
    public ResponseDto<UserCreditTransactionDto> deposit(Long userId, BigDecimal amount, String description) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return new ResponseDto<>(false, null, "Deposit amount must be positive");
        }
        UserCreditTransactionDto transactionDto = new UserCreditTransactionDto();
        transactionDto.setUserId(userId);
        transactionDto.setAmount(amount);
        transactionDto.setDescription("Deposit: " + description);
        UserCreditTransactionDto createdTransaction = createTransaction(transactionDto);
        return new ResponseDto<>(true, createdTransaction, "Deposit successful");
    }

    @Override
    public ResponseDto<UserCreditTransactionDto> withdraw(Long userId, BigDecimal amount, String description) {
        BigDecimal currentCredit = getCurrentCredit(userId);
        if (amount.compareTo(currentCredit) > 0) {
            return new ResponseDto<>(false, null, "Insufficient credit for withdrawal");
        }

        UserCreditTransactionDto transactionDto = new UserCreditTransactionDto();
        transactionDto.setUserId(userId);
        transactionDto.setAmount(amount.negate());
        transactionDto.setDescription("Withdrawal: " + description);
        UserCreditTransactionDto createdTransaction = createTransaction(transactionDto);
        return new ResponseDto<>(true, createdTransaction, "Withdrawal successful");
    }


    @Override
    public UserCreditTransactionDto createTransaction(UserCreditTransactionDto transactionDto) {
        UserCreditTransactionEntity transactionEntity = new UserCreditTransactionEntity();
        UserEntity user = userService.getUserEntityById(transactionDto.getUserId());

        transactionEntity.setUser(user);
        transactionEntity.setAmount(transactionDto.getAmount());
        transactionEntity.setTransactionDate(LocalDateTime.now());
        transactionEntity.setDescription(transactionDto.getDescription());

        UserCreditTransactionEntity savedEntity = transactionRepository.save(transactionEntity);
        return mapEntityToDto(savedEntity);
    }

    @Override
    public List<UserCreditTransactionDto> getTransactionsByUser(Long userId) {
        UserEntity user = userService.getUserEntityById(userId);
        return transactionRepository.findByUser(user).stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal getCurrentCredit(Long userId) {
        List<UserCreditTransactionDto> transactions = getTransactionsByUser(userId);
        return transactions.stream()
                .map(UserCreditTransactionDto::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public List<UserCreditTransactionDto> getDepositHistory(Long userId) {
        UserEntity user = userService.getUserEntityById(userId);
        return transactionRepository.findByUser(user).stream()
                .filter(transaction -> transaction.getAmount().compareTo(BigDecimal.ZERO) > 0)
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserCreditTransactionDto> getWithdrawalHistory(Long userId) {
        UserEntity user = userService.getUserEntityById(userId);
        return transactionRepository.findByUser(user).stream()
                .filter(transaction -> transaction.getAmount().compareTo(BigDecimal.ZERO) < 0)
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    private UserCreditTransactionDto mapEntityToDto(UserCreditTransactionEntity entity) {
        UserCreditTransactionDto dto = new UserCreditTransactionDto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUser().getId());
        dto.setAmount(entity.getAmount());
        dto.setTransactionDate(entity.getTransactionDate());
        dto.setDescription(entity.getDescription());
        return dto;
    }
}
