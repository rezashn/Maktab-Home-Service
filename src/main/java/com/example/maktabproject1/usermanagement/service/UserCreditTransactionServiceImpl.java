package com.example.maktabproject1.usermanagement.service;

import com.example.maktabproject1.ResponseDto;
import com.example.maktabproject1.common.ErrorMessage;
import com.example.maktabproject1.common.exception.BadRequestException;
import com.example.maktabproject1.usermanagement.Repository.UserCreditTransactionRepository;
import com.example.maktabproject1.usermanagement.dto.UserCreditTransactionDto;
import com.example.maktabproject1.usermanagement.entity.UserCreditTransactionEntity;
import com.example.maktabproject1.usermanagement.entity.UserEntity;
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
            throw new BadRequestException(ErrorMessage.INVALID_DATA_INPUT);
        }

        UserCreditTransactionDto dto = new UserCreditTransactionDto();
        dto.setUserId(userId);
        dto.setAmount(amount);
        dto.setDescription("Deposit: " + description);
        UserCreditTransactionDto result = createTransaction(dto);

        return new ResponseDto<>(true, result, "Deposit successful");
    }

    @Override
    public ResponseDto<UserCreditTransactionDto> withdraw(Long userId, BigDecimal amount, String description) {
        BigDecimal currentCredit = getCurrentCredit(userId);
        if (amount.compareTo(currentCredit) > 0) {
            throw new BadRequestException(ErrorMessage.INSUFFICIENT_CREDIT);
        }

        UserCreditTransactionDto dto = new UserCreditTransactionDto();
        dto.setUserId(userId);
        dto.setAmount(amount.negate());
        dto.setDescription("Withdrawal: " + description);
        UserCreditTransactionDto result = createTransaction(dto);

        return new ResponseDto<>(true, result, "Withdrawal successful");
    }

    @Override
    public UserCreditTransactionDto createTransaction(UserCreditTransactionDto dto) {
        if (dto == null || dto.getUserId() == null || dto.getAmount() == null) {
            throw new BadRequestException(ErrorMessage.INVALID_DATA_INPUT);
        }

        UserEntity user = userService.getUserEntityById(dto.getUserId());

        UserCreditTransactionEntity entity = new UserCreditTransactionEntity();
        entity.setUser(user);
        entity.setAmount(dto.getAmount());
        entity.setTransactionDate(LocalDateTime.now());
        entity.setDescription(dto.getDescription());

        return mapEntityToDto(transactionRepository.save(entity));
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
        UserEntity user = userService.getUserEntityById(userId);
        return transactionRepository.findByUser(user).stream()
                .map(UserCreditTransactionEntity::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public List<UserCreditTransactionDto> getDepositHistory(Long userId) {
        UserEntity user = userService.getUserEntityById(userId);
        return transactionRepository.findByUserAndAmountGreaterThan(user, BigDecimal.ZERO).stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserCreditTransactionDto> getWithdrawalHistory(Long userId) {
        UserEntity user = userService.getUserEntityById(userId);
        return transactionRepository.findByUserAndAmountLessThan(user, BigDecimal.ZERO).stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserCreditTransactionDto> getTransactionsBetween(Long userId, LocalDateTime start, LocalDateTime end) {
        UserEntity user = userService.getUserEntityById(userId);
        return transactionRepository.findByUserAndTransactionDateBetween(user, start, end).stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserCreditTransactionDto> getDepositsBetween(Long userId, LocalDateTime start, LocalDateTime end) {
        UserEntity user = userService.getUserEntityById(userId);
        return transactionRepository.findByUserAndAmountGreaterThanAndTransactionDateBetween(user, BigDecimal.ZERO, start, end).stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserCreditTransactionDto> getWithdrawalsBetween(Long userId, LocalDateTime start, LocalDateTime end) {
        UserEntity user = userService.getUserEntityById(userId);
        return transactionRepository.findByUserAndAmountLessThanAndTransactionDateBetween(user, BigDecimal.ZERO, start, end).stream()
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
