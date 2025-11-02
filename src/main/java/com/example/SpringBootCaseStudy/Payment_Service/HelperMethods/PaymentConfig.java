package com.example.SpringBootCaseStudy.Payment_Service.HelperMethods;

import com.example.SpringBootCaseStudy.Payment_Service.Enums.PaymentType;
import com.example.SpringBootCaseStudy.Payment_Service.Interface.IPaymentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class PaymentConfig {

    @Bean
    public Map<PaymentType, IPaymentHandler> handlerMap(@Autowired(required = false) List<IPaymentHandler> handlers) {
        Map<PaymentType, IPaymentHandler> map = new HashMap<>();
        for (IPaymentHandler handler : handlers) {
            if (handler.getClass().isAnnotationPresent(org.springframework.beans.factory.annotation.Qualifier.class)) {
                String qualifierValue = handler.getClass().getAnnotation(org.springframework.beans.factory.annotation.Qualifier.class).value();
                try {
                    PaymentType type = PaymentType.valueOf(qualifierValue);
                    map.put(type, handler);
                } catch (IllegalArgumentException e) {
                    System.err.println("Handler with qualifier " + qualifierValue + " does not match a PaymentType enum.");
                }
            } else {
                System.out.println(handler.getClass().getCanonicalName());
            }
        }
        return map;
    }
}