package com.example.maktabproject1.servicemanagement.controller;

import com.example.maktabproject1.servicemanagement.service.PaymentService;
import com.example.maktabproject1.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/pay")
    public ResponseEntity<ResponseDto<String>> payForOrder(
            @RequestParam Long orderId,
            @RequestParam String captchaAnswer,
            @RequestParam boolean useCredit) {

        try {
            String paymentResult = paymentService.processPayment(orderId, captchaAnswer, useCredit);
            return ResponseEntity.ok(new ResponseDto<>(true, paymentResult, "Payment processed successfully"));
        } catch (Exception e) {
            logger.error("Error processing payment for order {}: {}", orderId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto<>(false, null, "Error processing payment: " + e.getMessage()));
        }
    }
}
