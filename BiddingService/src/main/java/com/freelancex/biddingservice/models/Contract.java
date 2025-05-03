package com.freelancex.biddingservice.models;

import com.fasterxml.jackson.annotation.JsonView;
import com.freelancex.biddingservice.enums.ContractStatus;
import com.freelancex.biddingservice.views.Views;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "contracts")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "contract_id", updatable = false, nullable = false)
    @JsonView({Views.BaseView.class})
    private UUID contractId;

    @Setter
    @Column(name = "bid_id", unique = true, nullable = false)
    @JsonView({Views.BaseView.class})
    private UUID bidId;

    @Setter
    @Column(name = "job_id", unique = true, nullable = false)
    @JsonView({Views.BaseView.class})
    private UUID jobId;

    @OneToOne
    @JoinColumn(name = "bid_id", insertable = false, updatable = false)
    @JsonView({Views.ClientContractView.class, Views.FreelancerContractView.class})
    private Bid bid;

    @OneToOne
    @JoinColumn(name = "job_id", insertable = false, updatable = false)
    @JsonView({Views.ClientContractView.class, Views.FreelancerContractView.class})
    private Job job;

    @Setter
    @Column(nullable = false, length = 500)
    @JsonView({Views.BaseView.class})
    private String terms;

    @Setter
    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    @JsonView({Views.BaseView.class})
    private ContractStatus status = ContractStatus.ACTIVE;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false, columnDefinition = "TIMESTAMPTZ")
    @JsonView({Views.BaseView.class})
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMPTZ")
    @JsonView({Views.BaseView.class})
    private LocalDateTime updatedAt;
}