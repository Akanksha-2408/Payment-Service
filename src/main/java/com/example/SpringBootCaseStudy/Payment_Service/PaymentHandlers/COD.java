package com.example.SpringBootCaseStudy.Payment_Service.PaymentHandlers;

import com.example.SpringBootCaseStudy.Payment_Service.DTO.TransactionDTO;
import com.example.SpringBootCaseStudy.Payment_Service.Entity.OTP.Otp;
import com.example.SpringBootCaseStudy.Payment_Service.Entity.Payment;
import com.example.SpringBootCaseStudy.Payment_Service.Entity.PaymentDetails;
import com.example.SpringBootCaseStudy.Payment_Service.Entity.User;
import com.example.SpringBootCaseStudy.Payment_Service.Interface.PaymentInterface;
import com.example.SpringBootCaseStudy.Payment_Service.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class COD implements PaymentInterface {

    @Autowired
    UserService userService;

    @Autowired
    Transactions transaction;

    Map<String, String> otpCODMemory =  new HashMap<String, String>();
    String mode = "COD";
    Payment payment =  new Payment();
    TransactionDTO transactionDTO = new TransactionDTO();

    @Override
    public TransactionDTO initiatePayment(String mode, double amount, PaymentDetails details) {

        String optGenerated = "";

        //validate
        boolean validateEmail = false;
//        User userData = userService.getUser(details.getUser().getEmail());

        if (details.getUser() != null && details.getUser().getEmail() != null) {
            User userData = userService.getUser(details.getUser().getEmail());
            if (userData != null) {
                validateEmail = true;
            }
        }

        //send OTP
        if(validateEmail){
            Otp otp = new Otp();
            optGenerated = otp.GenerateOtp(4);
        }

        otpCODMemory.put(details.getUser().getEmail(), optGenerated);

        transactionDTO.setOtp(optGenerated);
        transactionDTO.setAmount(amount);

        return transactionDTO;
    }

    @Override
    public Double completePayment(String mode, TransactionDTO object, PaymentDetails details) {
//        CODValidationDTO codValidationDTO = (CODValidationDTO) request;
        Double balance = 0.0;
        String emailNow = details.getUser().getEmail();
        if(otpCODMemory.containsKey(emailNow)) {
            String checkOtp = otpCODMemory.get(emailNow);
            if(checkOtp.equals(object.getOtp())) {
                balance = transaction.TransferMoney(details, object.getAmount());
                payment.setStatus("COMPLETE");
                otpCODMemory.remove(emailNow); //once the transaction gets completed
            } else {
                payment.setStatus("Incorrect OTP");
            }
        }
        return balance;
    }

}
