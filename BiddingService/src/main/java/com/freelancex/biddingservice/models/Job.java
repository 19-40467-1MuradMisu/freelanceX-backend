package com.freelancex.biddingservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

enum JobStatus {
    OPEN,
    CLOSED
}

@Entity
@Table(name = "jobs")
@Getter
public class Job {

    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "job_id", nullable = false, updatable = false)
    private UUID jobId;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobStatus status = JobStatus.OPEN;

    @Setter
    @Column(nullable = false)
    private Double budget;
}

