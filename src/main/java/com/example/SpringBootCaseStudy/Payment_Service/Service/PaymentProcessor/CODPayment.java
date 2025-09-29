package com.example.SpringBootCaseStudy.Payment_Service.Service.PaymentProcessor;

import com.example.SpringBootCaseStudy.Payment_Service.DTO.PaymentRequest;
import com.example.SpringBootCaseStudy.Payment_Service.DTO.TransactionDTO;
import com.example.SpringBootCaseStudy.Payment_Service.Entity.Payment;
import com.example.SpringBootCaseStudy.Payment_Service.Entity.User;
import com.example.SpringBootCaseStudy.Payment_Service.Interface.IPaymentHandler;
import com.example.SpringBootCaseStudy.Payment_Service.OTP.Otp;
import com.example.SpringBootCaseStudy.Payment_Service.Repository.PaymentDetailsRepo;
import com.example.SpringBootCaseStudy.Payment_Service.Repository.PaymentRepo;
import com.example.SpringBootCaseStudy.Payment_Service.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CODPayment implements IPaymentHandler {

    @Autowired
    UserRepo userRepo;

    @Autowired
    PaymentRepo paymentRepo;

    @Override
    public String initiatePayment(PaymentRequest paymentRequest) {

        String otpGenerated = "";

        Enum payType = paymentRequest.getPaymentType();
        String email = paymentRequest.getEmail();
        Double  amount = paymentRequest.getAmount();

        Optional<User> validUser = userRepo.findByEmail(email);
        if(validUser.isPresent()){
            otpGenerated = Otp.generateOtp(4);
            Payment payment = new Payment();
            payment.setPayAmount(amount);
            payment.setPayMode(paymentRequest.getPaymentType());

            Payment savedPayment =   paymentRepo.save(payment);


            return  savedPayment.getTransactionId();

        }

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setOtp(otpGenerated);
//        transactionDTO.setTransactionId();

        return transactionDTO.getTransactionId();
    }

    @Override
    public void completePayment(TransactionDTO transactionDTO, PaymentRequest paymentRequest) {

    }
}
