package com.example.SpringBootCaseStudy.Payment_Service.Entity;

import com.example.SpringBootCaseStudy.Payment_Service.Enums.PaymentType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class PaymentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pid;

    @Enumerated(EnumType.STRING)
    PaymentType paymentType;

    //Card
    String cardNumber;
    String cvv;
    Date cardExpiryDate;

    //UPI
    String upiId;

    //Netbanking
    String username;
    String password;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
