package com.example.SpringBootCaseStudy.Payment_Service.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int uid;
    String name;
    String email;
    @Column(name = "contact")
    String mobile;
    Double balance;
}
