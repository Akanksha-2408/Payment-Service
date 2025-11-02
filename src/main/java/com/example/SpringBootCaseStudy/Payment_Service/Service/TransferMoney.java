package com.example.SpringBootCaseStudy.Payment_Service.Service;

import com.example.SpringBootCaseStudy.Payment_Service.Entity.Payment;
import com.example.SpringBootCaseStudy.Payment_Service.Entity.User;
import com.example.SpringBootCaseStudy.Payment_Service.Repository.PaymentRepo;
import com.example.SpringBootCaseStudy.Payment_Service.Repository.UserRepo;
import com.example.SpringBootCaseStudy.Payment_Service.Service.PaymentProcessor.CODPayment;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransferMoney {

    @Autowired
    PaymentRepo paymentRepo;

    @Autowired
    UserRepo userRepo;

    @Transactional
    public Double Transfer(String transactionId, Double amount) {

        System.out.println("Inside Transfer method");

        Optional<Payment> payment = paymentRepo.findByTransactionId(transactionId);

        if(payment.isEmpty()) {
            throw new IllegalArgumentException("Payment not found");
        }

        System.out.println("Checked inside Database");

        Payment pay = payment.get();
        double balance = pay.getUser().getBalance();

        if(balance < amount) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        double result = balance - amount;

        System.out.println("Transfered money");

        pay.getUser().setBalance(result);

        //update balance in user table
        pay.getUser().setBalance(result);
        userRepo.save(pay.getUser());

        pay.setStatus("COMPLETED");
        System.out.println("Status updated to completed");
        paymentRepo.save(pay);

        return result;
    }
}
