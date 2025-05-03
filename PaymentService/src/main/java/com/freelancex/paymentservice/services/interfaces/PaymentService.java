package com.freelancex.paymentservice.services.interfaces;

import com.freelancex.paymentservice.dtos.event.contract.CreateContractEvent;
import com.freelancex.paymentservice.models.Payment;

import java.util.List;
import java.util.UUID;

public interface PaymentService {
    void createPayment(CreateContractEvent event);

    void releasePayment(UUID paymentId);

    Payment getPaymentByContractId(UUID contractId);

    List<Payment> getPayments();
}
