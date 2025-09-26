package com.example.SpringBootCaseStudy.Payment_Service.Repository;

import com.example.SpringBootCaseStudy.Payment_Service.Entity.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDetailsRepo extends JpaRepository<PaymentDetails,Integer> {
}
