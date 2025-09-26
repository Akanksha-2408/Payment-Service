package com.example.SpringBootCaseStudy.Payment_Service.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class PayType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    String type;
}
