package com.freelancex.jobservice.services;

import com.freelancex.jobservice.dtos.event.job.CreateJobEvent;
import com.freelancex.jobservice.dtos.event.job.updateJobEvent;
import com.freelancex.jobservice.exceptions.ApiException;
import com.freelancex.jobservice.kafka.KafkaProducerServiceImpl;
import com.freelancex.jobservice.models.Job;
import com.freelancex.jobservice.repositories.JobRepository;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class JobServiceImpl  {

    // private static final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);

    private final JobRepository jobRepository;
    private final KafkaProducerServiceImpl kafkaProducer;

    public JobServiceImpl(JobRepository jobRepository,KafkaProducerServiceImpl kafkaProducer) {
        this.jobRepository = jobRepository;
        this.kafkaProducer = kafkaProducer;
    }


    public Job createJob(Job job) {
        job.setJobId(UUID.randomUUID());

        Job saveJob = jobRepository.save(job);
         CreateJobEvent createjobevent=new CreateJobEvent(saveJob.getJobId(),saveJob.getClientId(),saveJob.getStatus(),saveJob.getBudget(),saveJob.getTitle());
        this.kafkaProducer.sendJobCreatedEvent(createjobevent);
        return saveJob;
    }


    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }


    public Optional<Job> getJobById(UUID jobId) {
        return jobRepository.findById(jobId);
    }


    public Job updateJob(UUID jobId, Job jobDetails) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ApiException("Job not found", HttpStatus.NOT_FOUND));

        job.setTitle(jobDetails.getTitle());
        job.setDescription(jobDetails.getDescription());
        job.setBudget(jobDetails.getBudget());
        job.setLocation(jobDetails.getLocation());
        job.setStatus(jobDetails.getStatus());
        Job saveJob = jobRepository.save(job);
        updateJobEvent createjobevent=new updateJobEvent(saveJob.getJobId(),saveJob.getClientId(),saveJob.getStatus(),saveJob.getBudget(),saveJob.getTitle());
        this.kafkaProducer.sendJobUpdatedEvent(createjobevent);
        return saveJob;

       
    }


    public void deleteJob(UUID jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ApiException("Job not found", HttpStatus.NOT_FOUND));
        jobRepository.delete(job);
    }
}
