package com.example.SpringBootCaseStudy.Payment_Service.Service;

import com.example.SpringBootCaseStudy.Payment_Service.Enums.PaymentType;
import com.example.SpringBootCaseStudy.Payment_Service.Interface.IPaymentHandler;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class PaymentHandlerFactory {

    private final Map<PaymentType, IPaymentHandler> handlerMap;

    public PaymentHandlerFactory(Map<PaymentType, IPaymentHandler> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public IPaymentHandler getHandler(PaymentType paymentType) {

        IPaymentHandler handler = handlerMap.get(paymentType);

//        if(handler == null) {
//            throw new IllegalArgumentException("Payment type cannot be null in the request");
//        }

//        IPaymentHandler handler = handlerMap.get(paymentType);

        if (handler == null) {
            throw new IllegalArgumentException("No handler found for type: " + paymentType);
        }
        return handler;
    }
}