package com.example.SpringBootCaseStudy.Payment_Service.Controller;

import com.example.SpringBootCaseStudy.Payment_Service.DTO.PaymentRequest;
import com.example.SpringBootCaseStudy.Payment_Service.Service.PaymentHandlerFactory;
import com.example.SpringBootCaseStudy.Payment_Service.Service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/payment")
@Tag(name= "Payment Operations", description="Manages money transactions of all types which include COD, Card, UPI, Netbanking")
public class PaymentController {

    @Autowired
    PaymentHandlerFactory handlerFactory;

    @Autowired
    PaymentService paymentService;

    public PaymentController(PaymentHandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
    }

    @Operation(
            summary = "Initiate payment",
            description = "Handle and validate new payment request based on payment type and generate transactionId"
    )

    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Payment processed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request or insufficient funds",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error during processing",
                    content = @Content
            )
    })
    @PostMapping("/initiate")
    public String initiatePayment(@RequestBody PaymentRequest paymentRequest) {
        System.out.println("initiatePayment controller");
        return paymentService.initiate(paymentRequest);
    }

    @Operation(
            summary = "Complete payment",
            description = "Accepts transactionId and completes the payment"
    )

    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Payment Completed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request or insufficient funds",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error during processing",
                    content = @Content
            )
    })
    @PostMapping("/complete")
    public void completePayment(@RequestParam String transactionId, @RequestBody PaymentRequest paymentRequest){
        System.out.println("completePayment controller");
        paymentService.complete(transactionId, paymentRequest);
    }
}

