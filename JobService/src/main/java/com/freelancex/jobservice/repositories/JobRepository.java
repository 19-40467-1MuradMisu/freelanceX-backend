package com.freelancex.jobservice.repositories;

import com.freelancex.jobservice.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JobRepository extends JpaRepository<Job, UUID> {
    Optional<Job> findJobByJobIdAndClientId(UUID jobId, UUID clientId);
}
