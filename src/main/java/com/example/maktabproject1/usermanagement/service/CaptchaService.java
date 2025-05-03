package com.example.maktabproject1.usermanagement.service;

import java.util.concurrent.ThreadLocalRandom;

public class CaptchaService {

    public static int[] generateCaptcha() {
        int num1 = ThreadLocalRandom.current().nextInt(1, 100);
        int num2 = ThreadLocalRandom.current().nextInt(1, 100);
        return new int[]{num1, num2};
    }

    public static boolean validateCaptcha(int num1, int num2, int userAnswer) {
        return userAnswer == (num1 + num2);
    }
}
