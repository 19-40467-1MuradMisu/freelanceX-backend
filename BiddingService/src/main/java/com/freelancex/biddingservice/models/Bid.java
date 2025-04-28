package com.freelancex.biddingservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "bids")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
public class Bid {

    @Id
    @Column(name = "bid_id", updatable = false, nullable = false)
    private UUID bidId = UUID.randomUUID();

    @Setter
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "job_id", referencedColumnName = "job_id", nullable = false)
    private Job job;

    @Setter
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "freelancer_id", referencedColumnName = "user_id", nullable = false)
    private User freelancer;

    @Setter
    @Column(nullable = false)
    private Double amount;

    @Setter
    @Column(nullable = false, length = 1000)
    private String proposal;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false, columnDefinition = "TIMESTAMPTZ")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMPTZ")
    private LocalDateTime updatedAt;
}