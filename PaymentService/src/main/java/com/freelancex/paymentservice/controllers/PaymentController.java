package com.freelancex.paymentservice.controllers;

import com.freelancex.paymentservice.dtos.common.ApiResponse;
import com.freelancex.paymentservice.models.Payment;
import com.freelancex.paymentservice.services.interfaces.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/contract/{contractId}")
    public ResponseEntity<ApiResponse<Payment>> getPaymentByContractId(@PathVariable UUID contractId) {
        Payment payment = paymentService.getPaymentByContractId(contractId);

        ApiResponse<Payment> response = new ApiResponse<>("success", HttpStatus.OK.value(), payment);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("{id}/client/release")
    public ResponseEntity<ApiResponse> releasePayment(@PathVariable UUID id) {
        paymentService.releasePayment(id);

        ApiResponse response = new ApiResponse<>("success", HttpStatus.OK.value(), null);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
