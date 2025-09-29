package com.example.SpringBootCaseStudy.Payment_Service.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class PaymentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pid;

    @ManyToOne
    User user;

    String payType;

    //Card
    String cardNumber;
    String cvv;
    Date cardExpiryDate;

    //UPI
    String upiId;

    //Netbanking
    String username;
    String password;

    Double balance;

    public PaymentDetails() {}

    public PaymentDetails(String cardNumber, Date cardExpiryDate) {
        this.cardNumber = cardNumber;
        this.cardExpiryDate = cardExpiryDate;
    }

    public PaymentDetails(String upiId) {
        this.upiId = upiId;
    }

}
