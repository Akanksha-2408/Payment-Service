package com.example.SpringBootCaseStudy.Payment_Service.Repository;

import com.example.SpringBootCaseStudy.Payment_Service.Entity.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentDetailsRepo extends JpaRepository<PaymentDetails, Integer> {
    Optional<PaymentDetails> findPaymentDetailsByCvv(String cvv);
    Optional<PaymentDetails> findPaymentDetailsByUsername(String username);
    Optional<PaymentDetails> findPaymentDetailsByUpiId(String upiId);
}
