package com.freelancex.ratingservice.repositories;

import com.freelancex.ratingservice.models.Rating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RatingRepository extends JpaRepository<Rating, UUID> {
    List<Rating> findAllByJobId(UUID jobId);
    List<Rating> findAllByUserId(UUID userId);
    Optional<Rating> findByJobIdAndUserId(UUID jobId, UUID userId);
}
