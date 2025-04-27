package com.freelancex.biddingservice.services.interfaces;

import com.freelancex.biddingservice.models.Job;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JobService {
    List<Job> getAllJobs();

    Optional<Job> getJobById(UUID id);

    Job saveJob(Job job);

    void deleteJob(UUID id);
}
