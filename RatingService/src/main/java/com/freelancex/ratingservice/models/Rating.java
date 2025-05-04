package com.freelancex.ratingservice.models;

import com.freelancex.ratingservice.enums.Score;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "ratings", uniqueConstraints = @UniqueConstraint(columnNames = {"jobId", "userId"}))
@Getter
@Setter
public class Rating {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID ratingId;

    @Column(name = "job_id", nullable = false)
    private UUID jobId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Score score; 

    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;
}