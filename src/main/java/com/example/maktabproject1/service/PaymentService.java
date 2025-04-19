package com.example.maktabproject1.service;

import com.example.maktabproject1.entity.OrderEntity;
import com.example.maktabproject1.entity.OrderStatusType;
import com.example.maktabproject1.entity.UserEntity;
import com.example.maktabproject1.entity.UserCreditTransactionEntity;
import com.example.maktabproject1.repository.OrderRepository;
import com.example.maktabproject1.repository.UserCreditTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class PaymentService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserCreditTransactionRepository userCreditTransactionRepository;

    private static final int PAYMENT_TIMEOUT_MINUTES = 10;

    public String processPayment(Long orderId, String captchaAnswer, boolean useCredit) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getStatus().equals(OrderStatusType.WAITING_FOR_PAYMENT)) {
            return "Order is not in a valid state for payment.";
        }

        int[] captcha = CaptchaService.generateCaptcha();
        int num1 = captcha[0];
        int num2 = captcha[1];

        if (!CaptchaService.validateCaptcha(num1, num2, Integer.parseInt(captchaAnswer))) {
            return "Invalid CAPTCHA answer. Please try again.";
        }

        if (order.getPaymentRequestTime() != null &&
                order.getPaymentRequestTime().plus(PAYMENT_TIMEOUT_MINUTES, ChronoUnit.MINUTES).isBefore(LocalDateTime.now())) {
            order.setStatus(OrderStatusType.CANCELLED);
            orderRepository.save(order);
            return "Payment time has expired. Order is canceled.";
        }

        if (useCredit) {
            UserEntity user = order.getCustomer();
            BigDecimal userCreditBalance = calculateUserCreditBalance(user);

            if (userCreditBalance.compareTo(order.getSuggestedPrice()) >= 0) {
                UserCreditTransactionEntity transaction = new UserCreditTransactionEntity();
                transaction.setUser(user);
                transaction.setAmount(order.getSuggestedPrice().negate());
                transaction.setTransactionDate(LocalDateTime.now());
                transaction.setDescription("Payment for Order #" + orderId);
                userCreditTransactionRepository.save(transaction);

                order.setStatus(OrderStatusType.PAID);
                orderRepository.save(order);
                return "Payment successful with credits! Order is marked as PAID.";
            } else {
                return "Not enough credit to complete the payment.";
            }
        } else {
            boolean paymentSuccessful = processCardPayment(order.getSuggestedPrice());
            if (paymentSuccessful) {
                order.setStatus(OrderStatusType.PAID);
                orderRepository.save(order);
                return "Payment successful via card! Order is marked as PAID.";
            } else {
                return "Card payment failed. Please try again.";
            }
        }
    }

    private boolean processCardPayment(BigDecimal amount) {
        return true;
    }

    private BigDecimal calculateUserCreditBalance(UserEntity user) {
        BigDecimal balance = BigDecimal.ZERO;
        for (UserCreditTransactionEntity transaction : userCreditTransactionRepository.findByUser(user)) {
            balance = balance.add(transaction.getAmount());
        }
        return balance;
    }
}
