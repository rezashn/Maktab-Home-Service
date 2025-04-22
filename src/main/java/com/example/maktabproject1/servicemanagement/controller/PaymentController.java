package com.example.maktabproject1.servicemanagement.controller;

import com.example.maktabproject1.servicemanagement.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/process/{orderId}")
    public ResponseEntity<String> processPayment(
            @PathVariable Long orderId,
            @RequestParam int captchaNum1,
            @RequestParam int captchaNum2,
            @RequestParam String captchaAnswer,
            @RequestParam boolean useCredit) {

        String result = paymentService.processPayment(orderId, captchaNum1, captchaNum2, captchaAnswer, useCredit);
        return ResponseEntity.ok(result);
    }
}
