package com.freelancex.biddingservice.models;

import com.freelancex.biddingservice.enums.JobStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "jobs")
@Getter
public class Job {

    @Id
    @Setter
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