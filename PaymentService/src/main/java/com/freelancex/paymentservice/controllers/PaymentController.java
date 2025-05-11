package com.freelancex.paymentservice.controllers;

import com.freelancex.paymentservice.models.Payment;
import com.freelancex.paymentservice.services.interfaces.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Payment> getPaymentByContractId(@PathVariable UUID contractId) {
        Payment payment = paymentService.getPaymentByContractId(contractId);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    @PatchMapping("{id}/client/release")
    public ResponseEntity<Void> releasePayment(@PathVariable UUID id) {
        paymentService.releasePayment(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
