package com.freelancex.jobservice.service.impl;

import com.freelancex.jobservice.dtos.event.contract.CompletedContractEvent;
import com.freelancex.jobservice.dtos.event.job.CreateJobEvent;
import com.freelancex.jobservice.dtos.event.job.updateJobEvent;
import com.freelancex.jobservice.enums.JobStatus;
import com.freelancex.jobservice.exceptions.ApiException;
import com.freelancex.jobservice.kafka.KafkaProducerServiceImpl;
import com.freelancex.jobservice.models.Job;
import com.freelancex.jobservice.repositories.JobRepository;
import com.freelancex.jobservice.service.interfaces.JobService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class JobServiceImpl implements JobService {

    private static final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);

    private final JobRepository jobRepository;
    private final KafkaProducerServiceImpl kafkaProducer;

    public JobServiceImpl(JobRepository jobRepository, KafkaProducerServiceImpl kafkaProducer) {
        this.jobRepository = jobRepository;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public Job createJob(Job job) {
        Job saveJob = jobRepository.save(job);
        CreateJobEvent event = new CreateJobEvent(saveJob.getJobId(), saveJob.getClientId(),
                saveJob.getStatus(), saveJob.getBudget(), saveJob.getTitle());
        this.kafkaProducer.sendJobCreatedEvent(event);
        logger.info("Job: {} created", saveJob.getJobId());
        return saveJob;
    }

    @Override
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    @Override
    public Job getJobById(UUID jobId) {
        return jobRepository.findById(jobId)
                .orElseThrow(() -> new ApiException("Job not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public Job updateJob(UUID jobId, UUID clientId, Job jobDetails) {
        Job job = getJobByJobIdAndClientId(jobId, clientId);

        job.setTitle(jobDetails.getTitle());
        job.setDescription(jobDetails.getDescription());
        job.setBudget(jobDetails.getBudget());
        job.setLocation(jobDetails.getLocation());
        job.setStatus(jobDetails.getStatus());
        Job saveJob = jobRepository.save(job);
        updateJobEvent event = new updateJobEvent(saveJob.getJobId(), saveJob.getClientId(),
                saveJob.getStatus(), saveJob.getBudget(), saveJob.getTitle());
        this.kafkaProducer.sendJobUpdatedEvent(event);
        logger.info("Job: {} updated", saveJob.getJobId());
        return saveJob;
    }

    @Override
    public void deleteJob(UUID jobId, UUID clientId) {
        Job job = getJobByJobIdAndClientId(jobId, clientId);
        jobRepository.delete(job);
    }

    @Override
    public Job getJobByJobIdAndClientId(UUID jobId, UUID clientId) {
        return jobRepository.findJobByJobIdAndClientId(jobId, clientId)
                .orElseThrow(() -> new ApiException("Job not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Job> getJobByClientId(UUID clientId) {
        return jobRepository.findByClientIdOrderByCreatedAtDesc(clientId);
    }

    @Override
    public void closeJob(CompletedContractEvent event) {
        Job job = getJobById(event.jobId());

        job.setStatus(JobStatus.CLOSED);
        Job savedJob = jobRepository.save(job);
        logger.info("Job: {} closed", event.jobId());

        updateJobEvent event1 = new updateJobEvent(savedJob.getJobId(), savedJob.getClientId(),
                savedJob.getStatus(), savedJob.getBudget(), savedJob.getTitle());
        this.kafkaProducer.sendJobUpdatedEvent(event1);

        logger.info("Job: {} updated", event.jobId());
    }
}
