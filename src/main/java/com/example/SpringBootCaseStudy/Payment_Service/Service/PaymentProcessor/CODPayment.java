package com.example.SpringBootCaseStudy.Payment_Service.Service.PaymentProcessor;

import com.example.SpringBootCaseStudy.Payment_Service.DTO.PaymentRequest;
import com.example.SpringBootCaseStudy.Payment_Service.Entity.Payment;
import com.example.SpringBootCaseStudy.Payment_Service.Entity.User;
import com.example.SpringBootCaseStudy.Payment_Service.Enums.PaymentType;
import com.example.SpringBootCaseStudy.Payment_Service.Interface.IPaymentHandler;
import com.example.SpringBootCaseStudy.Payment_Service.HelperMethods.Otp;
import com.example.SpringBootCaseStudy.Payment_Service.Repository.PaymentRepo;
import com.example.SpringBootCaseStudy.Payment_Service.Repository.UserRepo;
import com.example.SpringBootCaseStudy.Payment_Service.Service.TransferMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@Qualifier("COD")
public class CODPayment implements IPaymentHandler {

    @Autowired
    UserRepo userRepo;

    @Autowired
    PaymentRepo paymentRepo;

    @Autowired
    TransferMoney transferMoney;

    static Map<String, User> codMemory = new HashMap<>();

    @Override
    public String initiatePayment(PaymentRequest paymentRequest) {  //paytype, amount, email

        System.out.println("CODPayment initiatePayment method");

        //RequestBody
        PaymentType payType = PaymentType.COD;
        Double amount = paymentRequest.getAmount();
        String email = paymentRequest.getEmail();

        User userData = new User();

        //Check email in database
        Optional<User> user = userRepo.findByEmail(email);
        if (user.isPresent()) {
            userData = user.get();
        } else {
            throw new IllegalArgumentException("User with email does not Exist");
        }

        String otpGenerated = Otp.generateOtp(4);
        System.out.println("OTP Generated: " + otpGenerated);

        // Create new Payment entity
        Payment payment = new Payment();

        // Generate transactionId manually
        String transactionId = UUID.randomUUID().toString();
        payment.setTransactionId(transactionId);

        payment.setPaymentType(PaymentType.COD);
        payment.setAmount(amount);
        payment.setStatus("INITIATED");
        payment.setUser(userData);

        // Save Payment entity to DB
        Payment savedPayment = paymentRepo.save(payment);

        // After save, get the transactionId
        String txnId = savedPayment.getTransactionId();

        codMemory.put(txnId, userData);

        return txnId;
    }

    @Override
    public void completePayment(String transactionId, PaymentRequest paymentRequest) {

        if(transactionId == null || paymentRequest == null) {
            throw new IllegalArgumentException("TransactionId and Payment request are mandatory fields");
        }

        if(codMemory.get(transactionId) == null) {
            throw new IllegalArgumentException("User object is NULL");
        }

        Double amount = paymentRequest.getAmount();

        System.out.println("Starting the transfer operation");

        Double balance = transferMoney.Transfer(transactionId, amount);

        if(balance == null) {
            throw new IllegalArgumentException("Invalid transactionId");
        }

        System.out.println("Updated Balance : " + balance);
    }

}
