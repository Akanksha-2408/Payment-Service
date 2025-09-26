package com.example.SpringBootCaseStudy.Payment_Service.Interface;

import com.example.SpringBootCaseStudy.Payment_Service.DTO.TransactionDTO;
import com.example.SpringBootCaseStudy.Payment_Service.Entity.Payment;
import com.example.SpringBootCaseStudy.Payment_Service.Entity.PaymentDetails;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface PaymentInterface {
    public TransactionDTO initiatePayment(String mode, double amount, PaymentDetails details);
    public Double completePayment(String mode, TransactionDTO object, PaymentDetails details);
    //public void cancelPayment(PaymentDetails details, String data);
}
