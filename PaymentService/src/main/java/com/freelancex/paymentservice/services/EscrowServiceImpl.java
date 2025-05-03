package com.freelancex.paymentservice.services;

import com.freelancex.paymentservice.enums.EscrowStatus;
import com.freelancex.paymentservice.models.Escrow;
import com.freelancex.paymentservice.repositories.EscrowRepository;
import com.freelancex.paymentservice.services.interfaces.EscrowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EscrowServiceImpl implements EscrowService {

    private final Logger logger = LoggerFactory.getLogger(EscrowServiceImpl.class);

    private final EscrowRepository escrowRepository;

    public EscrowServiceImpl(EscrowRepository escrowRepository) {
        this.escrowRepository = escrowRepository;
    }

    @Override
    public void createEscrow(UUID paymentId) {
        Escrow escrow = new Escrow();
        escrow.setPaymentId(paymentId);

        escrowRepository.save(escrow);
        logger.info("Escrow:{} created", escrow.getEscrowId());
    }

    @Override
    public boolean releaseEscrow(UUID paymentId) {
        Optional<Escrow> escrow = escrowRepository.findByPaymentId(paymentId);

        if (escrow.isPresent()) {
            Escrow escrowToUpdate = escrow.get();
            escrowToUpdate.setStatus(EscrowStatus.RELEASED);
            logger.info("Escrow:{} released", escrowToUpdate.getEscrowId());
            return true;
        }
        return false;
    }
}
