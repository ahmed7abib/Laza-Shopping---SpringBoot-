package com.ahmed.a.habib.lazaapp.utils;

import java.util.Random;

public class Utils {

    public static String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}