package com.example.SpringBootCaseStudy.Payment_Service.Interface;

import com.example.SpringBootCaseStudy.Payment_Service.DTO.PaymentRequest;
import com.example.SpringBootCaseStudy.Payment_Service.DTO.TransactionDTO;

public interface IPaymentHandler {
    public String initiatePayment(PaymentRequest paymentRequest);
    public void completePayment(TransactionDTO transactionDTO, PaymentRequest paymentRequest);
}
