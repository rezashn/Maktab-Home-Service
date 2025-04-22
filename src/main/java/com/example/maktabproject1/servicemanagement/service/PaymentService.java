package com.example.maktabproject1.servicemanagement.service;

import com.example.maktabproject1.servicemanagement.entity.OrderEntity;
import com.example.maktabproject1.servicemanagement.entity.OrderStatusType;
import com.example.maktabproject1.servicemanagement.repository.OrderRepository;
import com.example.maktabproject1.usermanagement.Repository.UserCreditTransactionRepository;
import com.example.maktabproject1.usermanagement.entity.UserCreditTransactionEntity;
import com.example.maktabproject1.usermanagement.entity.UserEntity;
import com.example.maktabproject1.usermanagement.service.CaptchaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class PaymentService {

    private static final int PAYMENT_TIMEOUT_MINUTES = 10;

    private final OrderRepository orderRepository;
    private final UserCreditTransactionRepository userCreditTransactionRepository;

    @Autowired
    public PaymentService(OrderRepository orderRepository,
                          UserCreditTransactionRepository userCreditTransactionRepository) {
        this.orderRepository = orderRepository;
        this.userCreditTransactionRepository = userCreditTransactionRepository;
    }

    @Transactional
    public String processPayment(Long orderId, int captchaNum1, int captchaNum2, String captchaAnswer, boolean useCredit) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        if (!order.getStatus().equals(OrderStatusType.WAITING_FOR_PAYMENT)) {
            return "Order is not in a valid state for payment.";
        }

        try {
            int userAnswer = Integer.parseInt(captchaAnswer);
            if (!CaptchaService.validateCaptcha(captchaNum1, captchaNum2, userAnswer)) {
                return "Invalid CAPTCHA answer. Please try again.";
            }
        } catch (NumberFormatException e) {
            return "CAPTCHA answer must be a number.";
        }

        if (order.getPaymentRequestTime() != null &&
                order.getPaymentRequestTime().plus(PAYMENT_TIMEOUT_MINUTES, ChronoUnit.MINUTES).isBefore(LocalDateTime.now())) {
            order.setStatus(OrderStatusType.CANCELLED);
            orderRepository.save(order);
            return "Payment time has expired. Order is canceled.";
        }

        return useCredit ? processCreditPayment(order) : processCardPayment(order);
    }

    private String processCreditPayment(OrderEntity order) {
        UserEntity user = order.getCustomer();
        BigDecimal creditBalance = calculateUserCreditBalance(user);
        BigDecimal orderPrice = order.getSuggestedPrice();

        if (creditBalance.compareTo(orderPrice) >= 0) {
            UserCreditTransactionEntity transaction = new UserCreditTransactionEntity();
            transaction.setUser(user);
            transaction.setAmount(orderPrice.negate()); // Deduct
            transaction.setTransactionDate(LocalDateTime.now());
            transaction.setDescription("Payment for Order #" + order.getId());

            userCreditTransactionRepository.save(transaction);

            order.setStatus(OrderStatusType.PAID);
            orderRepository.save(order);

            return "Payment successful with credits! Order is marked as PAID.";
        } else {
            return "Not enough credit to complete the payment.";
        }
    }

    private String processCardPayment(OrderEntity order) {
        boolean paymentSuccessful = simulateCardPayment(order.getSuggestedPrice());
        if (paymentSuccessful) {
            order.setStatus(OrderStatusType.PAID);
            orderRepository.save(order);
            return "Payment successful via card! Order is marked as PAID.";
        } else {
            return "Card payment failed. Please try again.";
        }
    }

    private boolean simulateCardPayment(BigDecimal amount) {
        return Math.random() < 0.9;
    }

    private BigDecimal calculateUserCreditBalance(UserEntity user) {
        return userCreditTransactionRepository.findByUser(user).stream()
                .map(UserCreditTransactionEntity::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
