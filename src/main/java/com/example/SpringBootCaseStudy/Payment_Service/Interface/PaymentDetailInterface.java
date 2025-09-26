package com.example.SpringBootCaseStudy.Payment_Service.Interface;

import com.example.SpringBootCaseStudy.Payment_Service.Entity.Payment;
import com.example.SpringBootCaseStudy.Payment_Service.Entity.PaymentDetails;

import java.util.List;

public interface PaymentDetailInterface {
    public List<Payment> getPaymentHistory(); //Payment Entity
    public List<PaymentDetails> getSavedPaymentDetails();
    public PaymentDetails UpdateSavedPaymentDetails();
    public PaymentDetails DeleteSavedPaymentDetails();
}
