package com.freelancex.paymentservice.repositories;

import com.freelancex.paymentservice.models.Escrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EscrowRepository extends JpaRepository<Escrow, UUID> {

    Optional<Escrow> findByPaymentId(UUID paymentId);
}
