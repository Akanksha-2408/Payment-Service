package com.example.SpringBootCaseStudy.Payment_Service.HelperMethods;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Otp {

    public  static  String generateOtp(int length) {
        String digits = "0123456789";
        Random random = new Random();
        StringBuilder otp = new StringBuilder();

        for(int i = 0; i < length; i++) {
            otp.append(digits.charAt(random.nextInt(digits.length())));
        }

        return otp.toString();
    }
}
