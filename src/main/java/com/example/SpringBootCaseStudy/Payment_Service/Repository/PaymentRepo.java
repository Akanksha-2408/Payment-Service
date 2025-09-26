package com.example.SpringBootCaseStudy.Payment_Service.Repository;

import com.example.SpringBootCaseStudy.Payment_Service.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment,Integer> {
}
