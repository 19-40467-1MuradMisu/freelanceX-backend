package com.freelancex.paymentservice.models;

import com.freelancex.paymentservice.enums.EscrowStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "escrows")
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Escrow {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "escrow_id", updatable = false, nullable = false)
    private UUID escrowId;

    @Setter
    @Column(name = "payment_id", updatable = false, nullable = false)
    private UUID paymentId;

    @Setter
    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private EscrowStatus status = EscrowStatus.HELD;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_id", referencedColumnName = "payment_id", nullable = false, unique = true)
    private Payment payment;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false, columnDefinition = "TIMESTAMPTZ")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMPTZ")
    private LocalDateTime updatedAt;
}
