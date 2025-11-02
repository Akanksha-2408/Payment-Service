package com.example.SpringBootCaseStudy.Payment_Service.Service;

import com.example.SpringBootCaseStudy.Payment_Service.DTO.PaymentRequest;
import com.example.SpringBootCaseStudy.Payment_Service.Enums.PaymentType;
import com.example.SpringBootCaseStudy.Payment_Service.Interface.IPaymentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PaymentService {

    private final Map<PaymentType, IPaymentHandler> handlerMap;

    @Autowired
    public PaymentService(Map<PaymentType, IPaymentHandler> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public String initiate(PaymentRequest paymentRequest){

        System.out.println("PaymentService initiate method");
        IPaymentHandler handler = handlerMap.get(paymentRequest.getPaymentType());

        if (handler == null) {
            throw new IllegalArgumentException("No handler found for type: " + paymentRequest.getPaymentType());
        }
        return handler.initiatePayment(paymentRequest);
    }

    public void complete(String transactionId, PaymentRequest paymentRequest){

        System.out.println("PaymentService complete method");
        IPaymentHandler handler = handlerMap.get(paymentRequest.getPaymentType());

        if (handler == null) {
            throw new IllegalArgumentException("No handler found for type: " + paymentRequest.getPaymentType());
        }
        handler.completePayment(transactionId, paymentRequest);
    }
}
