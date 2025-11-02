package com.example.SpringBootCaseStudy.Payment_Service.Repository;

import com.example.SpringBootCaseStudy.Payment_Service.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PaymentRepo extends JpaRepository<Payment,String> {

    Optional<Payment> findByTransactionId(String transactionId);

}
