package com.freelancex.biddingservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.UUID;

enum ContractStatus {
    ACTIVE, COMPLETED
}

@Entity
@Table(name = "contracts")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "contract_id", updatable = false, nullable = false)
    private UUID contractId;

    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bid_id", referencedColumnName = "bid_id", nullable = false)
    private Bid bid;

    @Setter
    @Column(nullable = false, length = 500)
    private String terms;

    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ContractStatus status = ContractStatus.ACTIVE;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime updatedAt;
}