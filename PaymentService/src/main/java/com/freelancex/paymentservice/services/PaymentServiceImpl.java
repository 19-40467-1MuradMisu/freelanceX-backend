package com.freelancex.paymentservice.services;

import com.freelancex.paymentservice.dtos.event.contract.CreateContractEvent;
import com.freelancex.paymentservice.dtos.event.payment.CompletePaymentEvent;
import com.freelancex.paymentservice.enums.ContractStatus;
import com.freelancex.paymentservice.exceptions.ApiException;
import com.freelancex.paymentservice.kafka.KafkaProducerImpl;
import com.freelancex.paymentservice.models.Payment;
import com.freelancex.paymentservice.repositories.PaymentRepository;
import com.freelancex.paymentservice.services.interfaces.EscrowService;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.UUID;

@Setter
public class PaymentServiceImpl {

    private final static Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class.getName());
    private final PaymentRepository paymentRepository;
    private final EscrowService escrowService;
    private final KafkaProducerImpl kafkaProducer;

    public PaymentServiceImpl(PaymentRepository paymentRepository, EscrowService escrowService,
                              KafkaProducerImpl kafkaProducer) {
        this.paymentRepository = paymentRepository;
        this.escrowService = escrowService;
        this.kafkaProducer = kafkaProducer;
    }

    public void createPayment(CreateContractEvent event) {
        Payment payment = new Payment();
        payment.setAmount(event.amount());
        payment.setContractId(event.contractId());

        Payment savedPayment = paymentRepository.save(payment);
        logger.info("Payment: {} create", savedPayment.getPaymentId());

        escrowService.createEscrow(savedPayment.getPaymentId());
    }

    public void updatePayment(UUID paymentId) {
        Optional<Payment> payment = paymentRepository.findById(paymentId);

        if (payment.isEmpty()) {
            throw new ApiException("Payment not found", HttpStatus.NOT_FOUND);
        }

        Payment paymentToUpdate = payment.get();

        boolean isReleased = escrowService.releaseEscrow(paymentToUpdate.getPaymentId());

        if (isReleased) {
            CompletePaymentEvent event = new CompletePaymentEvent(paymentToUpdate.getContractId(),
                    ContractStatus.COMPLETED);

            kafkaProducer.sendPaymentCompletedEvent(event);
        }
    }

    public Payment getPaymentByContractId(UUID contractId) {
        Optional<Payment> payment = paymentRepository.findByContractId(contractId);

        if (payment.isEmpty()) {
            throw new ApiException("Payment not found", HttpStatus.NOT_FOUND);
        }

        return payment.get();
    }
}
