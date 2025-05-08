package com.freelancex.ratingservice.models;

import com.freelancex.ratingservice.enums.Score;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ratings", uniqueConstraints = @UniqueConstraint(columnNames = {"jobId", "userId"}))
@EntityListeners(AuditingEntityListener.class)
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

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}