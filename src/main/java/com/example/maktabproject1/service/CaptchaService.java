package com.example.maktabproject1.service;

import java.util.Random;

public class CaptchaService {


    private static final Random random = new Random();

    public static int[] generateCaptcha() {
        int num1 = random.nextInt(100);  // Random number between 0 and 99
        int num2 = random.nextInt(100);
        return new int[]{num1, num2};
    }

    public static boolean validateCaptcha(int num1, int num2, int userAnswer) {
        return (num1 + num2) == userAnswer;
    }
}
