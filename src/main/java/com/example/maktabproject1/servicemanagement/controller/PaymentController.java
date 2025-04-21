package com.example.maktabproject1.servicemanagement.controller;

import com.example.maktabproject1.servicemanagement.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/pay")
    public String payForOrder(@RequestParam Long orderId, @RequestParam String captchaAnswer, @RequestParam boolean useCredit) {
        return paymentService.processPayment(orderId, captchaAnswer, useCredit);
    }
}
