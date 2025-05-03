package com.freelancex.paymentservice.services.interfaces;

import java.util.UUID;

public interface EscrowService {
    void createEscrow(UUID paymentId);

    boolean releaseEscrow(UUID paymentId);
}
