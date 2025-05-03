package com.freelancex.biddingservice.models;

import com.fasterxml.jackson.annotation.JsonView;
import com.freelancex.biddingservice.enums.JobStatus;
import com.freelancex.biddingservice.views.Views;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "jobs")
@Getter
@Setter
@JsonView({Views.BaseView.class})
public class Job {

    @Id
    @Column(name = "job_id", nullable = false, updatable = false)
    private UUID jobId;

    @Column(name = "client_id", nullable = false)
    private UUID clientId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private JobStatus status;

    @Column(nullable = false)
    private Double budget;

    @Column(nullable = false)
    private String title;
}