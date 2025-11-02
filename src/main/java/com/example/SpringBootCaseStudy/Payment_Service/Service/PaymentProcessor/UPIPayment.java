package com.example.SpringBootCaseStudy.Payment_Service.Service.PaymentProcessor;

import com.example.SpringBootCaseStudy.Payment_Service.DTO.PaymentRequest;
import com.example.SpringBootCaseStudy.Payment_Service.Entity.Payment;
import com.example.SpringBootCaseStudy.Payment_Service.Entity.PaymentDetails;
import com.example.SpringBootCaseStudy.Payment_Service.Entity.User;
import com.example.SpringBootCaseStudy.Payment_Service.Enums.PaymentType;
import com.example.SpringBootCaseStudy.Payment_Service.HelperMethods.Otp;
import com.example.SpringBootCaseStudy.Payment_Service.Interface.IPaymentHandler;
import com.example.SpringBootCaseStudy.Payment_Service.Repository.PaymentDetailsRepo;
import com.example.SpringBootCaseStudy.Payment_Service.Repository.PaymentRepo;
import com.example.SpringBootCaseStudy.Payment_Service.Service.TransferMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Qualifier("UPI")
public class UPIPayment implements IPaymentHandler {

    @Autowired
    PaymentDetailsRepo paymentDetailsRepo;

    @Autowired
    PaymentRepo paymentRepo;

    @Autowired
    TransferMoney transferMoney;

    Map<String, String> upiMemory = new HashMap<>();

    @Override
    public String initiatePayment(PaymentRequest paymentRequest) {

        //Extract and Validate Input
        PaymentType paymentType = paymentRequest.getPaymentType();
        System.out.println("Payment Type: " + paymentType);

        String upiId = paymentRequest.getUpiId();
        double amount = paymentRequest.getAmount();

        //Fetch payment details from db
        Optional<PaymentDetails> payDetailsOpt = paymentDetailsRepo.findPaymentDetailsByUpiId(upiId);

        //Check if details exist based on cvv
        if (payDetailsOpt.isEmpty()) {
            throw new IllegalArgumentException("Payment details not found (CVV mismatch)");
        }

        PaymentDetails payDetails = payDetailsOpt.get();

        //Final Validation Check
        if (payDetails.getUpiId().equals(upiId)) {

            //Generate OTP and get the User object
            String otpGenerated = Otp.generateOtp(4);
            System.out.println("OTP Generated: " + otpGenerated);
            User user = payDetails.getUser();

            //Create and Save Payment
            Payment payment = new Payment();
            payment.setTransactionId(UUID.randomUUID().toString());
            payment.setPaymentType(paymentType);
            payment.setStatus("INITIATED");
            payment.setAmount(amount);
            payment.setUser(user);

            Payment savedPayment = paymentRepo.save(payment);
            String txnId = savedPayment.getTransactionId();

            upiMemory.put(txnId, upiId);

            return txnId;
        } else {
            throw new IllegalArgumentException("Invalid UPI ID.");
        }
    }

    @Override
    public void completePayment(String transactionId, PaymentRequest paymentRequest) {

        if(transactionId == null || paymentRequest == null) {
            throw new IllegalArgumentException("TransactionId and Payment request are mandatory fields");
        }

        if(upiMemory.get(transactionId) == null) {
            throw new IllegalArgumentException("User object is NULL");
        }

        Double amount = paymentRequest.getAmount();

        Double balance = transferMoney.Transfer(transactionId, amount);

        if(balance == null) {
            throw new IllegalArgumentException("Invalid transactionId");
        }

        System.out.println("Updated Balance : " + balance);
    }
}
