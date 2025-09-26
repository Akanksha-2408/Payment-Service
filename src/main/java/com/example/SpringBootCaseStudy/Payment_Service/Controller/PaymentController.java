package com.example.SpringBootCaseStudy.Payment_Service.Controller;

import com.example.SpringBootCaseStudy.Payment_Service.DTO.TransactionDTO;
import com.example.SpringBootCaseStudy.Payment_Service.Entity.PaymentDetails;
import com.example.SpringBootCaseStudy.Payment_Service.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping("/initiate")
    public String initiatePayment(@RequestParam String mode, @RequestParam Double amount, @RequestBody PaymentDetails details) {
        TransactionDTO object = paymentService.makePayment(mode, amount, details);
        return object.getOtp();
    }

    @PostMapping("/complete")
    public Double completePayment(@RequestParam String mode, @RequestParam TransactionDTO object, @RequestBody PaymentDetails details) {
        return paymentService.completePay(mode, object, details);
    }
}
