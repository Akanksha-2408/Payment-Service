package com.example.SpringBootCaseStudy.Payment_Service.Service.PaymentProcessor;

import com.example.SpringBootCaseStudy.Payment_Service.DTO.PaymentRequest;
import com.example.SpringBootCaseStudy.Payment_Service.Entity.Payment;
import com.example.SpringBootCaseStudy.Payment_Service.Entity.PaymentDetails;
import com.example.SpringBootCaseStudy.Payment_Service.Entity.User;
import com.example.SpringBootCaseStudy.Payment_Service.Enums.PaymentType;
import com.example.SpringBootCaseStudy.Payment_Service.Interface.IPaymentHandler;
import com.example.SpringBootCaseStudy.Payment_Service.HelperMethods.Otp;
import com.example.SpringBootCaseStudy.Payment_Service.Repository.PaymentDetailsRepo;
import com.example.SpringBootCaseStudy.Payment_Service.Repository.PaymentRepo;
import com.example.SpringBootCaseStudy.Payment_Service.Repository.UserRepo;
import com.example.SpringBootCaseStudy.Payment_Service.Service.TransferMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@Qualifier("CARD")
public class CardPayment implements IPaymentHandler {

    @Autowired
    PaymentDetailsRepo paymentDetailsRepo;

    @Autowired
    PaymentRepo paymentRepo;

    @Autowired
    TransferMoney transferMoney;

    Map<String, String> cardMemory = new HashMap<>();

    @Override
    public String initiatePayment(PaymentRequest paymentRequest) {

        //Extract and Validate Input
        PaymentType paymentType = paymentRequest.getPaymentType();
        System.out.println("Payment Type: " + paymentType);

        String cardNumber = paymentRequest.getCardNumber();
        String cvv = paymentRequest.getCvv();
        double amount = paymentRequest.getAmount();

        //Fetch payment details from db
        Optional<PaymentDetails> payDetailsOpt = paymentDetailsRepo.findPaymentDetailsByCvv(cvv);

        //Check if details exist based on cvv
        if (payDetailsOpt.isEmpty()) {
            throw new IllegalArgumentException("Payment details not found (CVV mismatch)");
        }

        PaymentDetails payDetails = payDetailsOpt.get();

        //Prepare for Date Comparison (using the LocalDate fix)
        Date cardExpiryDateRequest = paymentRequest.getCardExpiryDate();
        Date cardExpiryDateDB = payDetails.getCardExpiryDate();

        // Convert both Date/Timestamp objects to LocalDate for YYYY-MM-DD comparison
        LocalDate requestDate = cardExpiryDateRequest.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate dbDate = cardExpiryDateDB.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        //Final Validation Check
        if (payDetails.getCardNumber().equals(cardNumber) && dbDate.equals(requestDate)) {

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

            cardMemory.put(txnId, cvv);

            return txnId;
        } else {
            throw new IllegalArgumentException("Invalid card number or expiry date.");
        }
    }

    @Override
    public void completePayment(String transactionId, PaymentRequest paymentRequest) {

        if(transactionId == null || paymentRequest == null) {
            throw new IllegalArgumentException("TransactionId and Payment request are mandatory fields");
        }

        if(cardMemory.get(transactionId) == null) {
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
