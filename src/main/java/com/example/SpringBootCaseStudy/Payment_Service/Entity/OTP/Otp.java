package com.example.SpringBootCaseStudy.Payment_Service.Entity.OTP;

import java.util.Random;

public class Otp {
    public String GenerateOtp(int length) {
        String digits = "0123456789";
        Random random = new Random();
        StringBuilder otp = new StringBuilder();

        for(int i = 0; i < length; i++) {
            otp.append(digits.charAt(random.nextInt(digits.length())));
        }

        return otp.toString();
    }
}
