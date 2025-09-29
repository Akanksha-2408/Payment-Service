package com.example.SpringBootCaseStudy.Payment_Service.DTO;

import lombok.Data;
import java.util.Date;

@Data
public class PaymentRequest {
    Enum paymentType;
    Double amount;

    //COD
    String email;

    //card
    String cardNumber;
    String cvv;
    Date cardExpiryDate;

    //UPI
    String upiId;

    //Netbanking
    String username;
    String password;
}
