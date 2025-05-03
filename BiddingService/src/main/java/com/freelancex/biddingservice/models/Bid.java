package com.freelancex.biddingservice.models;

import com.fasterxml.jackson.annotation.JsonView;
import com.freelancex.biddingservice.views.Views;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "bids", uniqueConstraints = @UniqueConstraint(columnNames = {"freelancer_id", "job_id"}))
@EntityListeners(AuditingEntityListener.class)
@Getter
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "bid_id", nullable = false)
    @JsonView(Views.BaseView.class)
    private UUID bidId;

    @Setter
    @Column(name = "freelancer_id", nullable = false)
    @JsonView(Views.BaseView.class)
    private UUID freelancerId;

    @Setter
    @Column(name = "job_id", nullable = false)
    @JsonView({Views.ClientBidView.class, Views.FreelancerBidView.class})
    private UUID jobId;

    @ManyToOne
    @JoinColumn(name = "job_id", insertable = false, updatable = false)
    @JsonView({Views.ClientBidView.class, Views.FreelancerBidView.class})
    private Job job;

    @ManyToOne
    @JoinColumn(name = "freelancer_id", insertable = false, updatable = false)
    @JsonView({Views.ClientBidView.class, Views.FreelancerBidView.class})
    private Freelancer freelancer;

    @OneToOne(mappedBy = "bid", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView({Views.ClientBidView.class, Views.FreelancerBidView.class})
    private Contract contract;

    @Setter
    @Column(nullable = false)
    @JsonView(Views.BaseView.class)
    private Double amount;

    @Setter
    @Column(nullable = false, length = 1000)
    @JsonView({Views.ClientBidView.class, Views.FreelancerBidView.class})
    private String proposal;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false, columnDefinition = "TIMESTAMPTZ")
    @JsonView(Views.BaseView.class)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMPTZ")
    @JsonView(Views.BaseView.class)
    private LocalDateTime updatedAt;
}