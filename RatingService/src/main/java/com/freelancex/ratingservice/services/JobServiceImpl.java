package com.freelancex.ratingservice.services;

import com.freelancex.ratingservice.dtos.event.job.CreateJobEvent;
import com.freelancex.ratingservice.dtos.event.job.updateJobEvent;
import com.freelancex.ratingservice.models.Job;
import com.freelancex.ratingservice.repositories.JobRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobServiceImpl {
    private final JobRepository jobRepository;
    private final static Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);

    @Autowired
    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }


    public void createJob(CreateJobEvent event) {
        Job job = new Job();

        job.setJobId(event.jobId());
        job.setBudget(event.budget());
        job.setTitle(event.title());
        job.setClientId(event.clientId());
        job.setStatus(event.status());

        jobRepository.save(job);
        logger.info("Job created: {}", job.getJobId());
    }

  
    public void updateJob(updateJobEvent event) {
        Optional<Job> job = jobRepository.findById(event.jobId());

        if (job.isPresent()) {
            Job jobToUpdate = job.get();
            jobToUpdate.setTitle(event.title());
            jobToUpdate.setStatus(event.status());
            jobToUpdate.setBudget(event.budget());

            jobRepository.save(jobToUpdate);
            logger.info("Job updated: {}", jobToUpdate.getJobId());
        }
    }
}
