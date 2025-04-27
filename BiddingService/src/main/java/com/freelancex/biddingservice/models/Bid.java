package com.freelancex.biddingservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "bids")
public class Bids {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID bidId;

    @Getter
    @ManyToOne
    @JoinColumn(name = "jobId")
    private Job job;

    @Getter
    @ManyToOne
    @JoinColumn(name = "userId")
    private User freelancer;

    @Getter
    @Setter
    private Double amount;

    @Getter
    @Setter
    private String proposal;

    @Getter
    @CreatedDate
    private LocalDateTime submittedAt;
}
