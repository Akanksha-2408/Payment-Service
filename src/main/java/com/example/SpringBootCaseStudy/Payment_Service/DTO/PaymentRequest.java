package com.example.SpringBootCaseStudy.Payment_Service.DTO;

import com.example.SpringBootCaseStudy.Payment_Service.Enums.PaymentType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import java.util.Date;

@Data
@Schema(description = "Request body for initiating a payment. Fields required depend on the 'payment_type'.")
public class PaymentRequest {

    @Enumerated(EnumType.STRING)
    @JsonProperty("payment_type")
    @Schema(description = "The required payment type: COD, CARD, UPI, or NETBANKING.", required = true)
    private PaymentType paymentType;

    @Schema(description = "The total amount for the transaction.", required = true, example = "150.75")
    Double amount;

    //COD
    @Schema(description = "User email, required for COD confirmation or general user lookup.", example = "user@example.com")
    String email;

    //card
    @JsonProperty("card_number")
    @Schema(description = "The 16-digit card number. Required only for CARD payment_type.", example = "4000123456789012")
    String cardNumber;

    @Schema(description = "The CVV code. Required only for CARD payment_type.", example = "123")
    String cvv;

    @JsonProperty("card_expiry_date")
    @Schema(description = "Card expiry date (MM/YY). Required only for CARD payment_type.", example = "2025-12-31")
    Date cardExpiryDate;

    //UPI
    @Schema(description = "The UPI Virtual Payment Address (VPA). Required only for UPI payment_type.", example = "user@bank")
    String upiId;

    //Netbanking
    @Schema(description = "Netbanking username. Required only for NETBANKING payment_type.", example = "netbankuser")
    String username;

    @Schema(description = "Netbanking password. Required only for NETBANKING payment_type.")
    String password;
}
