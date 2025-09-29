package com.example.SpringBootCaseStudy.Payment_Service.DTO;

import com.example.SpringBootCaseStudy.Payment_Service.Entity.PaymentDetails;
import lombok.Data;

@Data
public class completePaymentDTO {
    private TransactionDTO transactionDTO;
    private PaymentDetails paymentDetails;
}
