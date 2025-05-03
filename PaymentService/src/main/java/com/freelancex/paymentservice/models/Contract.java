package com.freelancex.paymentservice.models;

import com.freelancex.paymentservice.enums.ContractStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "contracts")
@Getter
@Setter
public class Contract {

    @Id
    @Column(name = "contract_id", updatable = false, nullable = false)
    private UUID contractId;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private ContractStatus status;

    @OneToOne(mappedBy = "contract", cascade = CascadeType.ALL, orphanRemoval = true)
    private Payment payment;
}
