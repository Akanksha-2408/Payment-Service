package com.example.SpringBootCaseStudy.Payment_Service.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PaymentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pdid;

    @ManyToOne
    User user;

    String payType;
    //COD - nothing new
    //Card
    String cardNumber;
    String cardLimit;
    String cvv;  //3-4 digit verification number on card
    //UPI
    String upiId;
    String pin;
    //Netbanking
    String username;
    String password;

    Double balance;
}
