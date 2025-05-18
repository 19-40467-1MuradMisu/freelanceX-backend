package com.freelancex.jobservice.service.interfaces;

import com.freelancex.jobservice.dtos.event.contract.CompletedContractEvent;
import com.freelancex.jobservice.models.Job;

import java.util.List;
import java.util.UUID;

public interface JobService {
    Job createJob(Job job);
    List<Job> getAllJobs();
    Job getJobById(UUID jobId);
    Job updateJob(UUID jobId, UUID clientId, Job jobDetails);
    void deleteJob(UUID jobId, UUID clientId);
    Job getJobByJobIdAndClientId(UUID jobId, UUID clientId);
    List<Job> getJobByClientId(UUID clientId);
    void closeJob(CompletedContractEvent event);
}
