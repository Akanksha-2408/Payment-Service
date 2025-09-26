package com.example.SpringBootCaseStudy.Payment_Service.Service;

import com.example.SpringBootCaseStudy.Payment_Service.DTO.TransactionDTO;
import com.example.SpringBootCaseStudy.Payment_Service.Entity.Payment;
import com.example.SpringBootCaseStudy.Payment_Service.Entity.PaymentDetails;
import com.example.SpringBootCaseStudy.Payment_Service.PaymentHandlers.COD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class PaymentService {

    Payment payment = new Payment();

    @Autowired
    COD codhandler;

//    @Autowired
//    Card cardhandler;
//
//    @Autowired
//    UPI upihandler;
//
//    @Autowired
//    Netbanking netbankinghandler;

    public TransactionDTO makePayment(@RequestParam String mode, @RequestParam double amount, @RequestBody PaymentDetails details) {
        if(mode.equalsIgnoreCase("COD")) {
            return codhandler.initiatePayment("COD", amount, details);
        } else {
            return null;
        }
//        } else if(mode.equalsIgnoreCase("Card")) {
//            cardhandler.initiatePayment("Card", details);
//        } else if(mode.equalsIgnoreCase("UPI")) {
//            upihandler.initiatePayment("UPI", details);
//        } else if(mode.equalsIgnoreCase("Netbanking")) {
//            netbankinghandler.initiatePayment("Netbanking", details);
//        } else {
//            return;
//        }
    }

    public Double completePay(@RequestParam String mode, @RequestParam TransactionDTO object, @RequestBody PaymentDetails details) {

        Double balance = null;

        if(mode.equalsIgnoreCase("COD")) {
            balance = codhandler.completePayment("COD", object, details);
        }
        return balance;
    }
}
