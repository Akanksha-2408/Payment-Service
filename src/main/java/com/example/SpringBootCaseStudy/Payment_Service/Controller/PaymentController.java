package com.example.SpringBootCaseStudy.Payment_Service.Controller;

import com.example.SpringBootCaseStudy.Payment_Service.DTO.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    public ResponseEntity<String> initiatePayment(@RequestBody PaymentRequest paymentRequest){
        //only call
        return null;
    }
}

