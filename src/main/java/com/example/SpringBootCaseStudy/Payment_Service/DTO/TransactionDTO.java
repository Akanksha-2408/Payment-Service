package com.example.SpringBootCaseStudy.Payment_Service.DTO;

import lombok.Data;

@Data
public class TransactionDTO {
    String otp;
    String transactionId;
}
