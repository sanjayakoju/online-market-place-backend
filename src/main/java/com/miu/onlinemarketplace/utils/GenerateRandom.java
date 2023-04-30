package com.miu.onlinemarketplace.utils;

import java.security.SecureRandom;
import java.util.Random;

public class GenerateRandom {

    static final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";


    public static String random() {
        Random random = new Random();
        int num = random.nextInt(100000);
        String formatted = String.format("%05d", num);
        System.out.println(formatted);
        return formatted;
    }

    public static String generateRandomAlphaNumericString(int n) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
        return sb.toString();
    }
}
