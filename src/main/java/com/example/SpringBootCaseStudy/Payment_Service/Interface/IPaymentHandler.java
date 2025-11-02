package com.example.SpringBootCaseStudy.Payment_Service.Interface;

import com.example.SpringBootCaseStudy.Payment_Service.DTO.PaymentRequest;

public interface IPaymentHandler {
    public String initiatePayment(PaymentRequest paymentRequest);
    public void completePayment(String transactionId, PaymentRequest paymentRequest);
}
