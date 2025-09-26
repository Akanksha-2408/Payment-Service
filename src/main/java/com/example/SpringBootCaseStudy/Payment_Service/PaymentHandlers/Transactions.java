package com.example.SpringBootCaseStudy.Payment_Service.PaymentHandlers;

import com.example.SpringBootCaseStudy.Payment_Service.Entity.Payment;
import com.example.SpringBootCaseStudy.Payment_Service.Entity.PaymentDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class Transactions {

    PaymentDetails paymentDetails;
    Payment payment;

    public Double TransferMoney(@RequestBody PaymentDetails details, @RequestParam double amount) {
        Double balance = null;
        if(details.getBalance() >= amount) {
            balance = details.getBalance() - amount;
        }
        payment.setBalance(balance);
        paymentDetails.setBalance(balance);
        return balance;
    }
}
